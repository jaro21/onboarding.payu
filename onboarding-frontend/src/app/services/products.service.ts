import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Product } from '../model/product';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  constructor(private http: HttpClient) { }

  parametros = {}

  url = '/shop/v1.0/products';

  getProducts() : Observable<any> {
    return this.http.get<Product[]>(this.url);
  }

  postProduct(name: string, code: string, description: string, price: number, stock: number, image: string) : Observable<any> {
    this.parametros = {
      'name': name,
      'code': code,
      'description': description,
      'price': price,
      'stock': stock,
      'photoUrl': image,
    }

    return this.http.post<Product>(this.url,this.parametros);
  }

  editProduct(id: number, name: string, code: string, description: string, price: number, stock: number, image: string) : Observable<any> {
    this.parametros = {
      'idProduct': id,
      'name': name,
      'code': code,
      'description': description,
      'price': price,
      'stock': stock,
      'photoUrl': image,
    }

    return this.http.put<Product>(this.url+"/"+id,this.parametros);
  }

  deleteProduct(id: number) : Observable<any> {
    return this.http.delete(this.url+"/"+id);
  }
}
