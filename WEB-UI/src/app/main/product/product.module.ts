import { NgModule, Type } from '@angular/core';
import { SharedModule } from '@shared';

import { ProductFormComponent } from './components/product-form/product-form.component';
import { ProductTableComponent } from './components/product-table/product-table.component';
import { ProductComponent } from './container/product.component';
import { ProductRoutingModule } from './product-routing.module';

@NgModule({
  declarations: [ProductComponent, ProductFormComponent, ProductTableComponent],
  imports: [SharedModule, ProductRoutingModule]
})
export class ProductModule { }
