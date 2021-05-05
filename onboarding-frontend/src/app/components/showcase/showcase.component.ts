import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table/public-api';
import { Product } from 'src/app/model/product';
import { PurchasOrderResponse } from 'src/app/model/purchase-order-response';
import { CartService } from 'src/app/services/cart.service';
import { PurchaseOrderService } from 'src/app/services/purchase-order.service';
import { PaymentComponent } from '../payment/payment.component';

@Component({
  selector: 'app-showcase',
  templateUrl: './showcase.component.html',
  styleUrls: ['./showcase.component.css']
})
export class ShowcaseComponent implements OnInit {

  products: any[] = [];
  cartTotal = 0;
  expanded = true;

  columns: string[] = ['product','price','quantity','total','remove'];
  dataSource!: MatTableDataSource<Product>;
  error = false;
  isSave = false;
  message: any;
  idOrder: number = -1;
  purchase!: PurchasOrderResponse;

  constructor(private cartService: CartService, private purchaseOrderService: PurchaseOrderService, 
              private dialogRef: MatDialogRef<ShowcaseComponent>, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.products = this.cartService.products;
    this.cartTotal = this.cartService.cartTotal;
  }

  deleteProduct(product: Product) {
    this.cartService.deleteProductFromCart(product);
    this.products = this.cartService.products;
    this.cartTotal = this.cartService.cartTotal;
  }

  openDialogPayment(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "900px";
    dialogConfig.data = this.purchase;
    const dialogRef = this.dialog.open(PaymentComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(result => {
      this.dialogRef.close(this.products);
      }
    )
  }

  save(){
    const idCustomer = sessionStorage.getItem('idCustomer');
    this.purchaseOrderService.save(Number(idCustomer), this.products).subscribe(
      response => {
        if(response.status && response.status == "SAVED"){
          this.isSave = true;
          this.message = "Save successful";
          this.idOrder = response.id;
          this.purchase = response;
          this.error = false;
        }else{
          this.message = response.error;
          this.error = true;
          this.isSave = false;
        }
      },
      err => {
        this.message = err.error.message;
        this.error = true;
        this.isSave = false;
      }  
    );
    //this.dialogRef.close(this.products);
  }
}