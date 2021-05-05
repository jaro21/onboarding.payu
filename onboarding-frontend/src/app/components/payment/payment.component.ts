import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Creditcard } from 'src/app/model/creditcard';
import { CreditCardEnum } from 'src/app/model/creditcard-enum';
import { PurchasOrderResponse } from 'src/app/model/purchase-order-response';
import { CreditcardService } from 'src/app/services/creditcard.service';
import { PaymentService } from 'src/app/services/payment.service';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {

  constructor(private dialog: MatDialog, @Inject(MAT_DIALOG_DATA) public data: PurchasOrderResponse,
              private creditCardService: CreditcardService, private paymentService: PaymentService) { }

  creditCards!: Creditcard[];
  cardSelected!: number;
  message: any;
  error = false;
  isSuccess = false;
  isProgress = false;

  name = new FormControl('', [Validators.required, Validators.maxLength(15)]);
  identificationNumber = new FormControl('', [Validators.required, Validators.pattern("[0-9]+")]);
  paymentMethod = new FormControl('', [Validators.required]);
  number = new FormControl('', [Validators.required, Validators.minLength(13), Validators.maxLength(16), Validators.pattern("[0-9]+")]);
  expirationDate = new FormControl('', [Validators.required, Validators.minLength(7), Validators.maxLength(7)]);
  installmentNumber = new FormControl('', [Validators.required, Validators.pattern("[0-9]+")]);
  cvv = new FormControl('', [Validators.required, Validators.pattern("[0-9]+"), Validators.maxLength(4)]);
  cards = ['VISA','DINERS','DISCOVER','AMEX','MASTERCARD','NARANJA','CODENSA'];
  numbers = Array.from({length: 32}, (_, k) => k + 1);
  saveCard = false;

  ngOnInit(): void {
    const dni = sessionStorage.getItem('dni');
    if(dni){
      this.creditCardService.getCreditCardsByCustomer(dni).subscribe(
        cards => {
          this.creditCards = cards;
        },
        err => console.log(err)
      )
    }
  }

  getErrorMessage() {
    if (this.number.hasError('pattern') || this.identificationNumber.hasError('pattern') || this.cvv.hasError('pattern')) {
      return 'You must enter a value numeric';
    }
    if (this.number.hasError('minLength') || this.number.hasError('maxLength')) {
      return 'The card number must be between 13 and 16 numeric characters.';
    }
    if (this.name.hasError('maxLength')) {
      return 'The size of the name must be a maximum of 15 characters.';
    }
    if (this.cvv.hasError('maxLength')) {
      return 'The size of the name must be a maximum of 4 characters.';
    }
    if (this.expirationDate.hasError('minLength') || this.expirationDate.hasError('maxLength')) {
      return 'Invalid Format.';
    }
    return 'You must enter a value';
  }

  getPaymentMethod(event: any){
    if(new RegExp(CreditCardEnum.VISA).test(this.number.value)){
      this.paymentMethod.setValue("VISA");
    }else if(new RegExp(CreditCardEnum.DINERS).test(this.number.value)){
      this.paymentMethod.setValue("DINERS");  
    }else if(new RegExp(CreditCardEnum.DISCOVER).test(this.number.value)){
      this.paymentMethod.setValue("DISCOVER");  
    }else if(new RegExp(CreditCardEnum.AMEX).test(this.number.value)){
      this.paymentMethod.setValue("AMEX");  
    }else if(new RegExp(CreditCardEnum.MASTERCARD).test(this.number.value)){
      this.paymentMethod.setValue("MASTERCARD");
    }else if(new RegExp(CreditCardEnum.NARANJA).test(this.number.value)){
      this.paymentMethod.setValue("NARANJA");
    }else if(new RegExp(CreditCardEnum.CODENSA).test(this.number.value)){
      this.paymentMethod.setValue("CODENSA");
    }
  }

  payment(){
    if(this.installmentNumber.valid){
      this.isProgress = true;
      console.log("CardSelected "+this.cardSelected);
      if(this.cardSelected == 0){
        this.paymentWithNewCard();
      }else{
        this.paymentWithToken();
      }
    }
  }

  paymentWithToken(){
    const idCustomer = sessionStorage.getItem('idCustomer');
    this.paymentService.applyPayment(Number(idCustomer), this.cardSelected, this.data.id, this.installmentNumber.value).subscribe(
      resp => {
        if(resp.status && resp.status === 'APPROVED'){
          this.message  = "Payment successful"
          this.isSuccess = true;
          this.error = false;
        }else{
          this.message = resp.error;
          this.error = true;
          this.isSuccess = false;
          this.isProgress = false;
        }
      },
      err => {
        this.message = err.error.message;
        this.error = true;
        this.isSuccess = false;
        this.isProgress = false;
      }
    )
  }

  paymentWithNewCard(){
    const idCustomer = sessionStorage.getItem('idCustomer');
    this.paymentService.applyPaymentNewCard(Number(idCustomer), this.cardSelected, this.data.id, this.name.value,
                                      this.identificationNumber.value, this.paymentMethod.value, this.number.value, 
                                      this.expirationDate.value, this.cvv.value, this.saveCard, this.installmentNumber.value).subscribe(
      response => {
        if(response.status && response.status == "APPROVED"){
          this.message  = "Payment successful"
          this.isSuccess = true;
          this.error = false;
        } else {
          this.message = response.error;
          this.error = true;
          this.isSuccess = false;
          this.isProgress = false;
        }
      },
      err => {
        this.message = err.error.message;
        this.error = true;
        this.isSuccess = false;
        this.isProgress = false;
      }
    )
  }
}
