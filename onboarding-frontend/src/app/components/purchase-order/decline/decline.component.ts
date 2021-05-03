import { Component, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PurchasOrderResponse } from 'src/app/model/purchase-order-response';
import { PurchaseOrderService } from 'src/app/services/purchase-order.service';

@Component({
  selector: 'app-decline',
  templateUrl: './decline.component.html',
  styleUrls: ['./decline.component.css']
})
export class DeclineComponent {

  constructor(private dialog: MatDialog, @Inject(MAT_DIALOG_DATA) public data: PurchasOrderResponse,
              private purchaseOrderService: PurchaseOrderService, private dialogRef: MatDialogRef<DeclineComponent>) { }

  message: any;
  error = false;
  isSuccess = false;

  decline(){
    const idCustomer = sessionStorage.getItem('idCustomer');
    this.purchaseOrderService.updateStatusById('DECLINED', this.data.id, Number(idCustomer)).subscribe(
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
