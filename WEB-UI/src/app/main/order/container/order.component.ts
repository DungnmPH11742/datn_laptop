import { Component, OnInit, ViewChild } from '@angular/core';

import { OrderFormComponent } from '../components/order-form/order-form.component';
import { OrderTableComponent } from '../components/order-table/order-table.component';
import { Order } from '../model/order';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styles: []
})
export class OrderComponent implements OnInit {
  @ViewChild(OrderFormComponent) orderForm: OrderFormComponent;

  @ViewChild(OrderTableComponent) orderTable: OrderTableComponent;
  filter: boolean = false;

  tab = 0;

  constructor() { }

  ngOnInit(): void { }

  showDetail(data: Order) {
    this.orderForm.order = data;
    this.orderForm.orderDetail = data.orderDetails[0];
    this.orderForm.getInsuranceDateProduct(data.completionDate);
    this.tab = 1;
  }

  getListOrder() {
    this.orderTable.findAll();
  }

  confirmOrder(data: Order) {
    this.orderForm.confirmOrder(data);
  }

  showFilter() {
    this.filter = !this.filter;
  }
}
