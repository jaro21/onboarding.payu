import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Product } from '../model/product';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  productMap = new Map();
  products: any[] = [];
  cartTotal = 0;

  private productAddedSource = new Subject<any>();

  productAdded$ = this.productAddedSource.asObservable();

  constructor() { }

  addProductToCart(product: Product) {
    let exists = false;
    this.cartTotal += product.price;
    
    this.products = this.products.map(_product => {
      if (_product.product.idProduct === product.idProduct) {
        _product.quantity++;
        exists = true;
      }
      return _product;
    });
    
    if (!exists) {
      this.products.push({
        product: product,
        quantity: 1
      });
    }
    this.productAddedSource.next({ products: this.products, cartTotal: this.cartTotal });
  }

  deleteProductFromCart(product: Product) {
    this.products = this.products.filter(_product => {
      if (_product.product.idProduct === product.idProduct) {
        this.cartTotal -= _product.product.price * _product.quantity;
        return false;
      }
      return true;
     });
    this.productAddedSource.next({ products: this.products, cartTotal: this.cartTotal });
  }

  flushCart() {
    this.products = [];
    this.cartTotal = 0;
    this.productAddedSource.next({ products: this.products, cartTotal: this.cartTotal });
  }
}
