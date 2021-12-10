import { Component, NgModule, OnInit, ViewChild } from '@angular/core';
import { VouchersFormComponent } from '../../components/vouchers-form/vouchers-form.component';
import { VoucherFormComponent } from '../components/voucher-form/voucher-form.component';
import { VoucherTableComponent } from '../components/voucher-table/voucher-table.component';


@Component({
  selector: 'app-voucher',
  templateUrl: './vouchers.component.html',
  styles: [
  ]
})
export class VouchersComponent implements OnInit {

  @ViewChild(VoucherFormComponent) formchild: VouchersFormComponent;
  @ViewChild(VoucherTableComponent) tableChild: VoucherTableComponent;
  constructor() { }

  ngOnInit(): void {
  }

}
