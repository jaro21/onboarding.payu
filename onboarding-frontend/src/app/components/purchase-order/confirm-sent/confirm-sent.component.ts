import { Component, Inject } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { PurchasOrderResponse } from 'src/app/model/purchase-order-response';
import { PurchaseOrderService } from 'src/app/services/purchase-order.service';

@Component({
  selector: 'app-confirm-sent',
  templateUrl: './confirm-sent.component.html',
  styleUrls: ['./confirm-sent.component.css']
})
export class ConfirmSentComponent {

  constructor(private dialog: MatDialog, @Inject(MAT_DIALOG_DATA) public data: PurchasOrderResponse,
              private purchaseOrderService: PurchaseOrderService, private dialogRef: MatDialogRef<ConfirmSentComponent>) { }

  message: any;
  error = false;
  isSuccess = false;

  sent(){
    this.purchaseOrderService.updateStatusById('SENT', this.data.id).subscribe(
      rest => {
        this.dialogRef.close();
      },
      err => {
        console.log(err);
        this.message = err.error.message;
        this.error = true;
      }  
    );
  }
}
