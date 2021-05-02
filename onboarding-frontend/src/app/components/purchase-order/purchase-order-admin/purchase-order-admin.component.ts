import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { PurchasOrderResponse } from 'src/app/model/purchase-order-response';
import { PaymentService } from 'src/app/services/payment.service';
import { PurchaseOrderService } from 'src/app/services/purchase-order.service';
import { ConfirmSentComponent } from '../confirm-sent/confirm-sent.component';

@Component({
  selector: 'app-purchase-order-admin',
  templateUrl: './purchase-order-admin.component.html',
  styleUrls: ['./purchase-order-admin.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class PurchaseOrderAdminComponent implements OnInit {

  constructor(private purchaseOrderService: PurchaseOrderService, private paymentService: PaymentService,
    private dialog: MatDialog) { }

  expandedElement!: PurchasOrderResponse | null;
  displayedColumns: string[] = ['dni','name','status', 'date', 'value', 'sent'];
  dataSource!: MatTableDataSource<PurchasOrderResponse>;
  @ViewChild(MatPaginator)
  paginator!: MatPaginator;
  @ViewChild(MatSort)
  sort!: MatSort;
  message: any;
  error = false;

  purchase: PurchasOrderResponse[] = [];
  
  ngOnInit(): void {
    this.purchaseOrderService.getPurchaseOrdersByStatus('PAID').subscribe(
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

  openDialogSent(order: PurchasOrderResponse) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = "900px";
    dialogConfig.data = order;
    const dialogRef = this.dialog.open(ConfirmSentComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    }
    )
  }

  getStyle(status: string): string {
    if (status == 'SAVED') {
      return "blue";
    } else if (status == 'PAID') {
      return "green";
    } else if (status == 'DECLINED') {
      return "red";
    } else if (status == 'REFUNDED') {
      return "grey";
    } else {
      return "yellow";
    }
  }
}
