import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PurchasOrderResponse } from 'src/app/model/purchase-order-response';
import { CreditcardService } from 'src/app/services/creditcard.service';
import { PaymentService } from 'src/app/services/payment.service';

@Component({
  selector: 'app-refund',
  templateUrl: './refund.component.html',
  styleUrls: ['./refund.component.css']
})
export class RefundComponent implements OnInit {

  constructor(private dialog: MatDialog, @Inject(MAT_DIALOG_DATA) public data: PurchasOrderResponse,
              private creditCardService: CreditcardService, private paymentService: PaymentService) { 
    console.log("Dialog data "+data.id);
  }

  ngOnInit(): void {
  }

  reason = new FormControl('', [Validators.required, Validators.maxLength(1024)]);
  message: any;
  error = false;
  isSuccess = false;
  isDisabled = false;

  getErrorMessage() {
    if(this.reason.hasError('required')){
      return 'You must enter a value';
    }
    return 'The size of reason must be maximum of 1024 characters.';
  }

  refund(){
    if(this.reason.valid){
      const idCustomer = sessionStorage.getItem('idCustomer');
      this.isDisabled = true;
      this.paymentService.applyRefund(Number(idCustomer), this.data.id, this.reason.value).subscribe(
        rest => {
          console.log(rest);
          if(rest.code === 'ERROR'){
            this.message = rest.error;
            this.error = true;
            this.isDisabled = false;
          }else{
            this.message = "Refund successful"
            this.isSuccess = true
          }
        },
        err => {
          this.message = err.error.message;
          this.error = true;
          this.isDisabled = false;
        }  
      );
    }
  }
}
