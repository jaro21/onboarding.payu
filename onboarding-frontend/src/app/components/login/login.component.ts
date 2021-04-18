import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Token } from '../../model/token';
import { FormBuilder } from '@angular/forms';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(private authService : AuthService, private fb: FormBuilder) { }
  
  token : Token | undefined ;

  form = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  });

  login(){
    localStorage.setItem('token', '');
    if (this.form.valid) {
      this.authService.obtenerToken(this.form.get('username')?.value, this.form.get('password')?.value).subscribe(
        token => {
          console.log(token);
          localStorage.setItem('token', token.token);
        },
        err => console.log(err)
      );
    }
  }

  logout(){
    localStorage.setItem('token', '');
  }
}
