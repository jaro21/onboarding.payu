import { AfterViewInit, OnInit, ViewChild } from '@angular/core';
import { Component } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Product } from 'src/app/model/product';
import { ProductsService } from 'src/app/services/products.service';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  displayedColumns: string[] = ['code','name','price','stock'];
  dataSource!: MatTableDataSource<Product>;

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;
  @ViewChild(MatSort)
  sort!: MatSort;
  products!: Product[];

  constructor(public productService: ProductsService) {}

  ngOnInit(): void {
    this.productService.getProducts()
      .subscribe(
        prods => {
          console.log("prods 1 : "+prods)
          this.products = prods;
          console.log("products 1 : "+this.products)
          this.dataSource = new MatTableDataSource(prods);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
        },
        err => console.log(err)
      )
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
}