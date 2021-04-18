import { Component } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { LoginComponent } from './components/login/login.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
  //, providers: [AuthService]
})
export class AppComponent {


  albumIds = [1, 2, 3];

  constructor(private router: Router, private dialog: MatDialog) { }

  title = 'onboarding-frontend';

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

    this.dialog.open(LoginComponent, dialogConfig);
  }

}
