import { Component, Inject, Input, OnInit } from '@angular/core';

import { OrderComponent } from '../../container/order.component';

@Component({
  selector: 'app-order-filter',
  templateUrl: './order-filter.component.html',
  styles: []
})
export class OrderFilterComponent implements OnInit {
  constructor(@Inject(OrderComponent) public parent: OrderComponent) { }

  ngOnInit(): void { }
}
