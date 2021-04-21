import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Product } from 'src/app/model/product';
import { CartService } from 'src/app/services/cart.service';

const OFFSET_HEIGHT = 170;
const PRODUCT_HEIGHT = 48;

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  products: any[] = [];
  numProducts = 0;
  animatePlop = false;
  animatePopout = false;
  expanded = false;
  expandedHeight: string = '0';
  cartTotal = 0;
  inherit!: string;


  changeDetectorRef: ChangeDetectorRef;


  constructor(private cartService: CartService, changeDetectorRef: ChangeDetectorRef) {
    this.changeDetectorRef = changeDetectorRef;
  }

  ngOnInit() {
    this.expandedHeight = '0';
    this.cartService.productAdded$.subscribe(data => {
      this.products = data.products;
      this.cartTotal = data.cartTotal;
      this.numProducts = data.products.reduce((acc: any, product: { quantity: any; }) => {
        acc += product.quantity;
        return acc;
      }, 0);

      // Make a plop animation
      if (this.numProducts > 1) {
        this.animatePlop = true;
        setTimeout(() => {
          this.animatePlop = false;
        }, 160);
      } else if (this.numProducts === 1) {
        this.animatePopout = true;
        setTimeout(() => {
          this.animatePopout = false;
        }, 300);
      }
      this.expandedHeight = (this.products.length * PRODUCT_HEIGHT + OFFSET_HEIGHT) + 'px';
      if (!this.products.length) {
        this.expanded = false;
      }
      this.changeDetectorRef.detectChanges();
    });
  }

  deleteProduct(product: Product) {
    this.cartService.deleteProductFromCart(product);
  }

  onCartClick() {
    this.expanded = !this.expanded;
  }
}
