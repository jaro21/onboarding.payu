import { Component, Inject } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Product } from 'src/app/model/product';
import { ProductsService } from 'src/app/services/products.service';

@Component({
  selector: 'app-delete-product',
  templateUrl: './delete-product.component.html',
  styleUrls: ['./delete-product.component.css']
})
export class DeleteProductComponent {

  constructor(private dialog: MatDialog, @Inject(MAT_DIALOG_DATA) public data: Product,
              private productService: ProductsService, private dialogRef: MatDialogRef<DeleteProductComponent>) { }

  message: any;
  error = false;
  isSuccess = false;

  delete(){
    this.productService.deleteProduct(this.data.idProduct).subscribe(
      response => {
        this.dialogRef.close();
      },
      err => {
        console.log(err);
        this.message = err.error.message;
        this.error = true;
      }  
    );
  }
}
