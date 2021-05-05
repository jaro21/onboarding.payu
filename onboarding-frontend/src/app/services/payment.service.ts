import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PurchasOrderResponse } from '../model/purchase-order-response';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private http: HttpClient) { }

  parametros = {}

  url = '/shop/v1.0/payments';
  url_refund = '/shop/v1.0/payments/refund';

  getPurchaseOrders(idCustomer: number) : Observable<any> {
    return this.http.get<PurchasOrderResponse[]>(this.url+"/customer-id/"+idCustomer);
  }

  applyPayment(idCustomer: number, idCard: number, idPurchaseOrder: number, installmentNumber: number) : Observable<any> {
    this.parametros = {
      'idCustomer': idCustomer,
      'idCreditCard': idCard,
      'idPurchaseOrder': idPurchaseOrder,
      'ipAddress': "127.0.0.1",
      'installmentNumber': installmentNumber,
    }
    console.log("Payment 1 "+this.parametros);
    return this.http.post<PurchasOrderResponse>(this.url,this.parametros);
  }

  applyPaymentNewCard(idCustomer: number, idCard: number, idPurchaseOrder: number, name: string, 
                      identificationNumber: number, paymentMethod: string, number: number, 
                      expirationDate: string, cvv: string, saveCard: boolean, installmentNumber: number) : Observable<any> {
    this.parametros = {
      "idCustomer": idCustomer,
      "idCreditCard": idCard,
      "idPurchaseOrder": idPurchaseOrder,
      "ipAddress": "127.0.0.1",
      "installmentNumber": installmentNumber,
      "creditCard": {
        "payerId": idCustomer,
        "name": name,
        "identificationNumber": identificationNumber,
        "paymentMethod": paymentMethod,
        "number": number,
        "expirationDate": expirationDate,
        "cvv": cvv,
        "saveCard": saveCard
      }
    }
    console.log("Payment Card "+this.parametros);
    return this.http.post<PurchasOrderResponse>(this.url,this.parametros);
  }

  applyRefund(idCustomer: number, idPurchaseOrder: number, reason: string) : Observable<any> {

    this.parametros = {
      'idCustomer': idCustomer,
      'idPurchaseOrder' : idPurchaseOrder,
      'reason' : reason
    }
    return this.http.post<PurchasOrderResponse>(this.url_refund,this.parametros);
  }
}
