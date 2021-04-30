import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptorService } from './auth-interceptor.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './components/login/login.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatDialogModule } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatBadgeModule } from '@angular/material/badge';
import { MatRadioModule } from '@angular/material/radio';
import { MatCheckboxModule } from '@angular/material/checkbox';

import { ProductComponent } from './components/product/product.component';
import { ListComponent } from './components/product/list/list.component';
import { AddComponent } from './components/product/add/add.component';
import { ShoppingCartComponent } from './components/shopping-cart/shopping-cart.component';
import { ShowcaseComponent } from './components/showcase/showcase.component';
import { AddCustomerComponent } from './components/customer/add-customer/add-customer.component';
import { ModifyCustomerComponent } from './components/customer/modify-customer/modify-customer.component';
import { AddCreditcardComponent } from './components/creditcard/add-creditcard/add-creditcard.component';
import { PurchaseOrderListComponent } from './components/purchase-order/purchase-order-list/purchase-order-list.component';
import { PaymentComponent } from './components/payment/payment.component';
import { RefundComponent } from './components/refund/refund.component';
import { DeclineComponent } from './components/purchase-order/decline/decline.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ProductComponent,
    ListComponent,
    AddComponent,
    ShoppingCartComponent,
    ShowcaseComponent,
    AddCustomerComponent,
    ModifyCustomerComponent,
    AddCreditcardComponent,
    PurchaseOrderListComponent,
    PaymentComponent,
    RefundComponent,
    DeclineComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule, 
    ReactiveFormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatCardModule,
    MatToolbarModule,
    MatInputModule,
    MatSelectModule,
    MatIconModule,
    MatMenuModule,
    MatTooltipModule,
    MatDialogModule,
    MatTableModule,
    MatPaginatorModule,
    MatBadgeModule,
    MatRadioModule,
    MatCheckboxModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptorService,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
