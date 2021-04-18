import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddComponent } from './components/product/add/add.component';
import { ListComponent } from './components/product/list/list.component';
import { ProductComponent } from './components/product/product.component';

const routes: Routes = [
  {path: 'list',component:ListComponent},
  {path: 'product',component:ProductComponent},
  {path: 'add-product', component:AddComponent}
  //{path: '**',component:ListComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
