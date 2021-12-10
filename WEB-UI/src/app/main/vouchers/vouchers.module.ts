import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { VouchersRoutingModule } from './vouchers-routing.module';
import { VoucherFormComponent } from './components/voucher-form/voucher-form.component';
import { VoucherTableComponent } from './components/voucher-table/voucher-table.component';
import { VouchersComponent } from './container/vouchers.component';
import { SharedModule } from '@shared';


@NgModule({
  declarations: [
    VoucherFormComponent,
    VoucherTableComponent,
    VouchersComponent
  ],
  imports: [
    SharedModule,
    VouchersRoutingModule
  ]
})
export class VouchersModule { }
