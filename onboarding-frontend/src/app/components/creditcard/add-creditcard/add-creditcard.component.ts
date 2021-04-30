import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Creditcard } from 'src/app/model/creditcard';
import { CreditCardEnum } from 'src/app/model/creditcard-enum';
import { CreditcardService } from 'src/app/services/creditcard.service';
import { LoginComponent } from '../../login/login.component';

@Component({
  selector: 'app-add-creditcard',
  templateUrl: './add-creditcard.component.html',
  styleUrls: ['./add-creditcard.component.css']
})
export class AddCreditcardComponent {

  constructor(private creditcardService: CreditcardService, private dialogRef: MatDialogRef<LoginComponent>) { }
  
  cards = ['VISA','DINERS','DISCOVER','AMEX','MASTERCARD','NARANJA','CODENSA'];

  message: any;
  error = false;
  creditCard!: Creditcard;

  name = new FormControl('', [Validators.required, Validators.maxLength(15)]);
  identificationNumber = new FormControl('', [Validators.required, Validators.pattern("[0-9]+")]);
  paymentMethod = new FormControl('', [Validators.required]);
  number = new FormControl('', [Validators.required, Validators.minLength(13), Validators.maxLength(16), Validators.pattern("[0-9]+")]);
  expirationDate = new FormControl('', [Validators.required, Validators.minLength(7), Validators.maxLength(7)]);
  
  getErrorMessage() {
    if (this.number.hasError('pattern') || this.identificationNumber.hasError('pattern')) {
      return 'You must enter a value numeric';
    }
    if (this.number.hasError('minLength') || this.number.hasError('maxLength')) {
      return 'The card number must be between 13 and 16 numeric characters.';
    }
    if (this.name.hasError('maxLength')) {
      return 'The size of the name must be a maximum of 15 characters.';
    }
    if (this.expirationDate.hasError('minLength') || this.expirationDate.hasError('maxLength')) {
      return 'Invalid Format.';
    }
    return 'You must enter a value';
  }

  addCreditCard(){
    if(this.name.valid && this.identificationNumber.valid && this.paymentMethod != null 
      && this.number.valid && this.expirationDate.valid){
      
      const idCustomer = sessionStorage.getItem('idCustomer');

      this.creditcardService.addCreditCard(Number(idCustomer), this.name.value, this.identificationNumber.value, this.paymentMethod.value,
        this.number.value, this.expirationDate.value).subscribe(
          response => {
            console.log(response);
            this.creditCard = response;
            this.dialogRef.close(this.name.value);
          },
          err => {
            console.log(err)
            this.message = err.error.message;
            this.error = true;
          }
        );
    }
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
}
