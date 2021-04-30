import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Product } from 'src/app/model/product';
import { CartService } from 'src/app/services/cart.service';
import { ShowcaseComponent } from '../showcase/showcase.component';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  products: any[] = [];
  numProducts = 0;
  cartTotal = 0;

  changeDetectorRef: ChangeDetectorRef;

  constructor(private cartService: CartService, changeDetectorRef: ChangeDetectorRef, private dialog: MatDialog) {
    this.changeDetectorRef = changeDetectorRef;
  }

  ngOnInit() {
    this.cartService.productAdded$.subscribe(data => {
      this.products = data.products;
      this.cartTotal = data.cartTotal;
      this.numProducts = data.products.reduce((acc: any, product: { quantity: any; }) => {
        acc += product.quantity;
        return acc;
      }, 0);

      this.changeDetectorRef.detectChanges();
    });
  }

  deleteProduct(product: Product) {

    this.cartService.deleteProductFromCart(product);
  }

  openDialogDetalCart() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = "900px";
    const dialogRef = this.dialog.open(ShowcaseComponent, dialogConfig);
    dialogRef.afterClosed().subscribe(result => {
        window.location.reload;
      }
    )
  }
}
