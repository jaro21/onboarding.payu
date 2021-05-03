import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PurchasOrderResponse } from '../model/purchase-order-response';

@Injectable({
  providedIn: 'root'
})
export class PurchaseOrderService {

  constructor(private http: HttpClient) { }

  parametros = {}

  url = '/shop/v1.0/purchase-orders';
  url_decline = '/shop/v1.0/purchase-orders/decline';

  getPurchaseOrdersByStatus(status: string) : Observable<any> {
    return this.http.get<PurchasOrderResponse[]>(this.url+"?status="+status);
  }

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

  updateStatusById(status: string, idPurchaseOrder: number, idCustomer: number) : Observable<any> {
    this.parametros = {
      'status': status,
      'idCustomer': idCustomer
    }
    return this.http.patch(this.url+"/"+idPurchaseOrder, this.parametros);
  }
}

function buildProducts(prod : any): any {
  return {
    idProduct: prod.product.idProduct, 
    quantity: prod.quantity
  };
}
