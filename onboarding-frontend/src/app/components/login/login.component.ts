import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Token } from '../../model/token';
import { FormControl } from '@angular/forms';
import { Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  mensajeError: any;
  constructor(private authService : AuthService, private dialogRef: MatDialogRef<LoginComponent>) { }
  
  token : Token | undefined ;
  hide = true;
  error = false;

  username = new FormControl('', [Validators.required]);
  password = new FormControl('', [Validators.required]);

  getErrorMessage() {
    return 'You must enter a value';
  }

  login(){
    sessionStorage.removeItem('token');
    if (this.username.valid && this.password.valid) {
      this.authService.obtenerToken(this.username.value, this.password.value).subscribe(
        token => {
          console.log(token);
          sessionStorage.setItem('token', token.token);
          sessionStorage.setItem('isLogin', 'true');
          sessionStorage.setItem('fullname', token.name);
          sessionStorage.setItem('role', token.role);
          sessionStorage.setItem('dni', token.dni);
          this.dialogRef.close(this.username.value);
        },
        err => {
          console.log(err)
          this.mensajeError = err.error.message;
          this.error = true;
        }
      );
    }
  }
}
