import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Product } from 'src/app/model/product';
import { ProductsService } from 'src/app/services/products.service';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent {
  product!: Product;
  prod!: Product;

  constructor(public productService : ProductsService, private fb: FormBuilder) { }

  form = this.fb.group({
    name: ['', Validators.required],
    code: ['', Validators.required],
    description: ['', Validators.required],
    price: [null, Validators.required],
    stock: [null, Validators.required],
    image: ['', Validators.required]
  });

  add(){
    if (this.form.valid) {
      this.productService.postProduct(this.form.get('name')?.value, this.form.get('code')?.value,
      this.form.get('description')?.value, this.form.get('price')?.value, this.form.get('stock')?.value,
      this.form.get('image')?.value).subscribe(
        rest => {
          console.log(rest);
          this.product = rest;
        },
        err => console.log(err)
      );
    }
  }
}
