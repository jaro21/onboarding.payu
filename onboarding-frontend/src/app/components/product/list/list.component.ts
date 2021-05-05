import { Component, Input, OnInit } from '@angular/core';
import { Product } from 'src/app/model/product';
import { CartService } from 'src/app/services/cart.service';
import { ProductsService } from 'src/app/services/products.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  productsView : Product[] = [];
  
  constructor(public productService : ProductsService, private cartService: CartService) { }

  ngOnInit(): void {
    this.productService.getProducts().subscribe(
        products => {
          this.productsView = products;
        },
        err => console.log(err)
      )
  }

  getProducts(){
    this.productService.getProducts()
      .subscribe(
        products => {
          this.productsView = products;
        },
        err => console.log(err)
      )
  }

  onAddToCart(product: Product) {
    this.cartService.addProductToCart(product);
    
    this.productsView.forEach(_product => {
      if (_product.idProduct === product.idProduct) {
        if(_product.quantity){
          _product.quantity++;
        }else{
          _product.quantity = 1;
        }
      }
    });
  }

  onRemoveToCart(product: Product) {
    this.cartService.deleteProductFromCart(product);

    this.productsView.forEach(_product => {
      if (_product.idProduct === product.idProduct) {
        _product.quantity--;
      }
    });
  }
}
