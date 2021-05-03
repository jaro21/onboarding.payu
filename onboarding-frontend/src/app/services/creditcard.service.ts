import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Creditcard } from '../model/creditcard';

@Injectable({
  providedIn: 'root'
})
export class CreditcardService {

  constructor(private http: HttpClient) { }

  parametros = {}

  url = '/shop/v1.0/credit-cards';

  addCreditCard(payerId: number, name: string, identificationNumber: number, paymentMethod: string, 
                number: number, expirationDate: string) : Observable<any> {

    this.parametros = {
      'payerId': payerId,
      'name': name,
      'identificationNumber': identificationNumber,
      'paymentMethod': paymentMethod,
      'number': number,
      'expirationDate': expirationDate
    }

    return this.http.post<Creditcard>(this.url,this.parametros);
  }

  getCreditCardsByCustomer(dni: string) : Observable<any> {
    return this.http.get<Creditcard[]>(this.url+"?dni="+dni);
  }
}
