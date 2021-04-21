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

  @Input()
  product!: Product;
  
  products : Product[] = [];

  constructor(public productService : ProductsService, private cartService: CartService) { }

  ngOnInit(): void {
    this.productService.getProducts()
      .subscribe(
        products => {
          console.log(products)
          this.products = products;
        },
        err => console.log(err)
      )
  }

  getProducts(){
    this.productService.getProducts()
      .subscribe(
        products => {
          console.log(products)
          this.products = products;
        },
        err => console.log(err)
      )
  }

  onAddToCart(product: Product) {
    console.log('product name '+product.name);
    this.cartService.addProductToCart(product);
    this.products.map(_product => {
      if (_product.idproduct === product.idproduct) {
        console.log('quantity '+_product.name);
        if(_product.quantity){
          _product.quantity++;
        }else{
          _product.quantity = 1;
        }
      }
      console.log('quantity 2 '+_product.quantity);
      return _product;
    });
  }

  onRemoveToCart(product: Product) {
    this.cartService.deleteProductFromCart(product);
    this.products.map(_product => {
      if (_product.idproduct === product.idproduct) {
        _product.quantity--;
      }
      return _product;
    });
  }
}
