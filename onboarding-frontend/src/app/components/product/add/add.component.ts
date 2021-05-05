import { Component, Inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
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
  message: any;
  error = false;
  isEdit = false;
  isSuccess = false;

  constructor(public productService : ProductsService, private fb: FormBuilder, private dialogRef: MatDialogRef<AddComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Product) {
      if(data){
        this.isEdit = true;
        this.form.controls['name'].setValue(data.name);
        this.form.controls['code'].setValue(data.code);
        this.form.controls['description'].setValue(data.description);
        this.form.controls['price'].setValue(data.price);
        this.form.controls['stock'].setValue(data.stock);
        this.form.controls['image'].setValue(data.image);
      }
  }

  form = this.fb.group({
    name: ['', Validators.required],
    code: ['', Validators.required],
    description: ['', Validators.required],
    price: [null, Validators.required],
    stock: [null, Validators.required],
    image: ['', Validators.required]
  });

  save(){
    if (this.form.valid) {
      if(this.isEdit){
        this.edit();
      }else{
        this.add();
      }
    }  
  }

  edit(){
    this.productService.editProduct(this.data.idProduct, this.form.get('name')?.value, this.form.get('code')?.value,
    this.form.get('description')?.value, this.form.get('price')?.value, this.form.get('stock')?.value,
    this.form.get('image')?.value).subscribe(
      rest => {
        console.log(rest);
        this.product = rest;
        this.message  = "Saved successfully"
        this.isSuccess = true;
      },
      err => {
        console.log(err)
        this.message = err.error.message;
        this.error = true;
      }  
    );
  }

  add(){
    this.productService.postProduct(this.form.get('name')?.value, this.form.get('code')?.value,
    this.form.get('description')?.value, this.form.get('price')?.value, this.form.get('stock')?.value,
    this.form.get('image')?.value).subscribe(
      rest => {
        console.log(rest);
        this.product = rest;
        this.message  = "Saved successfully"
        this.isSuccess = true;
      },
      err => {
        console.log(err)
        this.message = err.error.message;
        this.error = true;
      }  
    );
  }
}