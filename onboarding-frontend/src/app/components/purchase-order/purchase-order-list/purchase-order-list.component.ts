import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { PurchasOrderResponse } from 'src/app/model/purchase-order-response';
import { PurchaseOrderService } from 'src/app/services/purchase-order.service';
import {animate, state, style, transition, trigger} from '@angular/animations';
import { PaymentService } from 'src/app/services/payment.service';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { PaymentComponent } from '../../payment/payment.component';
import { RefundComponent } from '../../refund/refund.component';
import { DeclineComponent } from '../decline/decline.component';

@Component({
  selector: 'app-purchase-order-list',
  templateUrl: './purchase-order-list.component.html',
  styleUrls: ['./purchase-order-list.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class PurchaseOrderListComponent implements OnInit {

  constructor(private purchaseOrderService: PurchaseOrderService, private paymentService: PaymentService, 
              private dialog: MatDialog) { }
  
  expandedElement!: PurchasOrderResponse | null;
  displayedColumns: string[] = ['status','date','value','decline','payment','refund'];
  dataSource!: MatTableDataSource<PurchasOrderResponse>;
  @ViewChild(MatPaginator)
  paginator!: MatPaginator;
  @ViewChild(MatSort)
  sort!: MatSort;
  message: any;
  error = false;

  purchase: PurchasOrderResponse[] = [];
  ngOnInit(): void {
    const idCustomer = sessionStorage.getItem('idCustomer');
    this.purchaseOrderService.getPurchaseOrders(Number(idCustomer)).subscribe(
      resp => {
        this.dataSource = new MatTableDataSource(resp);
        this.purchase = resp;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      err => {
        console.log(err);
        this.message = err.error.message;
        this.error = true;
      }
    );
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  openDialogPayment(order: PurchasOrderResponse){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "900px";
    dialogConfig.data = order;
    const dialogRef = this.dialog.open(PaymentComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(result => {
        this.ngOnInit();
      }
    )
  }

  openDialogRefund(order: PurchasOrderResponse){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "900px";
    dialogConfig.data = order;
    const dialogRef = this.dialog.open(RefundComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(result => {
        this.ngOnInit();
      }
    )
  }

  openDialogDecline(order: PurchasOrderResponse){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = "900px";
    dialogConfig.data = order;
    const dialogRef = this.dialog.open(DeclineComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(result => {
        this.ngOnInit();
      }
    )
  }
}
