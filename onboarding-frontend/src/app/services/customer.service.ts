import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Customer } from '../model/customer';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient) { }

  parametros = {}

  url = '/shop/v1.0/customers';

  postCustomer(fullName: string, email: string, phone: string, dniNumber: string, address: string, city: string, state: string, 
              country: string, postal_code: string, username: string, password: string) : Observable<any> {

    this.parametros = {
      'fullName': fullName,
      'email': email,
      'phone': phone,
      'dniNumber': dniNumber,
      'address': address,
      'city': city,
      'state': state,
      'country': country,
      'postal_code': postal_code,
      'username': username,
      'password': password
    }

    return this.http.post<Customer>(this.url,this.parametros);
  }
}
