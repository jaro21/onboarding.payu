import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Customer } from 'src/app/model/customer';
import { CustomerService } from 'src/app/services/customer.service';
import { LoginComponent } from '../../login/login.component';

@Component({
  selector: 'app-add-customer',
  templateUrl: './add-customer.component.html',
  styleUrls: ['./add-customer.component.css']
})
export class AddCustomerComponent implements OnInit {
  mensajeError: any;
  constructor(private customerService: CustomerService, private fb: FormBuilder, private dialogRef: MatDialogRef<LoginComponent>) { }

  hide = true;
  error = false;
  customer!: Customer;
  
  fullName = new FormControl('', [Validators.required]);
  email = new FormControl('', [Validators.required, Validators.email]);
  phone = new FormControl('', [Validators.required]);
  dniNumber = new FormControl('', [Validators.required]);
  address = new FormControl('', [Validators.required]);
  city = new FormControl('', [Validators.required]);
  state = new FormControl('', [Validators.required]);
  country = new FormControl('', [Validators.required]);
  postal_code = new FormControl('', [Validators.required]);
  username = new FormControl('', [Validators.required]);
  password = new FormControl('', [Validators.required]);
  
  
  getErrorMessage() {
    if (this.email.hasError('required')) {
      return 'You must enter a value';
    } 

    if(this.email.hasError('email')){
      return  'Not a valid email';  
    }

    return 'You must enter a value';
  }

  ngOnInit(): void {
  }

  addCustomer(){
    this.customerService.postCustomer(this.fullName.value, this.email.value, this.phone.value, this.dniNumber.value, 
                                      this.address.value, this.city.value, this.state.value, this.country.value, 
                                      this.postal_code.value, this.username.value, this.password.value).subscribe(
      response => {
        console.log(response);
        this.customer = response;
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
