import { NgModule } from '@angular/core';

import { SaleProductComponent } from './container/sale-product.component';
import { SaleProductRoutingModule } from './sale-product-routing.module';
import { SaleProductFormComponent } from './components/sale-product-form/sale-product-form.component';
import { SaleProductTableComponent } from './components/sale-product-table/sale-product-table.component';
import { SharedModule } from '@shared';


@NgModule({
  declarations: [
    SaleProductComponent,
    SaleProductFormComponent,
    SaleProductTableComponent
  ],
  imports: [
    SharedModule,
    SaleProductRoutingModule
  ]
})
export class SaleProductModule { }
