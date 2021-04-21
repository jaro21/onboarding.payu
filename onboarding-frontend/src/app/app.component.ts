import { importExpr } from '@angular/compiler/src/output/output_ast';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { AddComponent } from './components/product/add/add.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
  //, providers: [AuthService]
})
export class AppComponent implements OnInit {

  constructor(private router: Router, private dialog: MatDialog) { }

  title = 'onboarding-frontend';

  isShowLogin: boolean = true;

  currentRole = 'USER';

  ngOnInit(): void {
    if(sessionStorage.getItem('isLogin') === 'true'){
      this.isShowLogin = false;
    }else{
      this.isShowLogin = true;
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
    const dialogRef = this.dialog.open(AddComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(result => {
        window.location.reload();
      }
    )
  }
}
