import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../model/product';
import { PurchasOrderResponse } from '../model/purchase-order-response';

@Injectable({
  providedIn: 'root'
})
export class PurchaseOrderService {

  constructor(private http: HttpClient) { }

  parametros = {}

  url = '/shop/v1.0/purchase-orders';
  url_decline = '/shop/v1.0/purchase-orders/decline';

  getPurchaseOrders(idCustomer: number) : Observable<any> {
    return this.http.get<PurchasOrderResponse[]>(this.url+"/customer-id/"+idCustomer);
  }

  save(idCustomer: number, products: any[]) : Observable<any> {
    const prods = products.map(prod => buildProducts(prod));
    this.parametros = {
      'idCustomer': idCustomer,
      'productList': prods,
    }
    return this.http.post<PurchasOrderResponse>(this.url,this.parametros);
  }

  decline(idCustomer: number, idPurchaseOrder: number) : Observable<any> {
    this.parametros = {
      'idCustomer': idCustomer,
      'idPurchaseOrder': idPurchaseOrder,
    }
    return this.http.post<PurchasOrderResponse>(this.url_decline,this.parametros);
  }
}

function buildProducts(prod : any): any {
  return {
    idProduct: prod.product.idProduct, 
    quantity: prod.quantity
  };
}
