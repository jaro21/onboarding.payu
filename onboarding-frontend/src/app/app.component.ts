import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AddCreditcardComponent } from './components/creditcard/add-creditcard/add-creditcard.component';
import { AddCustomerComponent } from './components/customer/add-customer/add-customer.component';
import { LoginComponent } from './components/login/login.component';
import { AddComponent } from './components/product/add/add.component';
import { PurchaseOrderListComponent } from './components/purchase-order/purchase-order-list/purchase-order-list.component';
import { CartService } from './services/cart.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
  //, providers: [AuthService]
})
export class AppComponent implements OnInit {

  constructor(private router: Router, private dialog: MatDialog, private cartService: CartService) { }

  title = 'onboarding-frontend';

  isShowLogin: boolean = true;

  currentRole = 'USER';

  name!: string;

  ngOnInit(): void {
    if(sessionStorage.getItem('isLogin') === 'true'){
      this.isShowLogin = false;
      const fullname = sessionStorage.getItem('fullname');
      if(fullname){
        this.name = fullname;
      }
    }else{
      this.isShowLogin = true;
      this.name = "";
    }
    
    if(sessionStorage.getItem('role') === 'ADMIN'){
      this.currentRole = 'ADMIN';
    }else{
      this.currentRole = 'USER';
    }
  }

  listar() {
    this.router.navigate(['product']);
  }

  list() {
    this.router.navigate(['list']);
  }

  openDialogLogin() {

    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    const dialogRef = this.dialog.open(LoginComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
      //this.name = sessionStorage.getItem('fullname');
      if(sessionStorage.getItem('token')!=null && sessionStorage.getItem('isLogin')){
        this.isShowLogin = false;
        if(sessionStorage.getItem('role') === 'ADMIN'){
          this.currentRole = 'ADMIN';
        }else{
          this.currentRole = 'USER';
        }
      }
      console.log('isLogin '+sessionStorage.getItem('isLogin'));
      console.log('isShowLogin '+this.isShowLogin);
      window.location.reload();
    });
  }

  logout(){
    console.log('logout')
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('isLogin');
    sessionStorage.removeItem('fullname');
    sessionStorage.removeItem('role');
    sessionStorage.removeItem('dni');
    window.location.reload();
  }

  openDialogAddProduct() {

    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "700px";
    const dialogRef = this.dialog.open(AddComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(result => {
        window.location.reload();
      }
    )
  }

  openDialogAddCustomer(){
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "900px";
    const dialogRef = this.dialog.open(AddCustomerComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(result => {
        window.location.reload();
      }
    )
  }

  openDialogAddCreditCard(){
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "700px";
    const dialogRef = this.dialog.open(AddCreditcardComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(result => {
        window.location.reload();
      }
    )
  }

  openDialogPurchaseOrders(){
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "900px";
    const dialogRef = this.dialog.open(PurchaseOrderListComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(result => {
        window.location.reload();
      }
    )
  }
}
