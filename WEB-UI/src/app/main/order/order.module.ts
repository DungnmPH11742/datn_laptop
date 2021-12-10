import { NgModule } from '@angular/core';
import { SharedModule } from '@shared';

import { OrderFormComponent } from './components/order-form/order-form.component';
import { OrderTableComponent } from './components/order-table/order-table.component';
import { OrderComponent } from './container/order.component';
import { OrderRoutingModule } from './order-routing.module';
import { OrderFilterComponent } from './components/order-filter/order-filter.component';

@NgModule({
  declarations: [OrderComponent, OrderFormComponent, OrderTableComponent, OrderFilterComponent],
  imports: [SharedModule, OrderRoutingModule]
})
export class OrderModule { }
