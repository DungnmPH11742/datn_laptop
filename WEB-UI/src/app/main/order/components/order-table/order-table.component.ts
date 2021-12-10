import { Component, Inject, OnInit } from '@angular/core';
import { CommonOrder } from 'src/app/main/common/common-order';

import { OrderComponent } from '../../container/order.component';
import { Order } from '../../model/order';
import { OrderService } from '../../service/order.service';

@Component({
  selector: 'app-order-table',
  templateUrl: './order-table.component.html',
  styles: []
})
export class OrderTableComponent implements OnInit {
  page = 1;
  pageSize = 5;
  listOfData: Order[] = [];

  commonOrder = new CommonOrder();
  constructor(private orderService: OrderService, @Inject(OrderComponent) public parent: OrderComponent) { }

  ngOnInit(): void {
    this.findAll();
  }

  findAll() {
    this.orderService.findAll().subscribe(
      (res: any) => {
        this.listOfData = res;
      },
      err => {
        console.log(err);
      }
    );
  }
}
