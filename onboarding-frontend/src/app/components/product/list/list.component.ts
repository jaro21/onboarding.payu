import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/model/product';
import { ProductsService } from 'src/app/services/products.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  products : Product[] = [];

  constructor(public productService : ProductsService) { }

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
}
