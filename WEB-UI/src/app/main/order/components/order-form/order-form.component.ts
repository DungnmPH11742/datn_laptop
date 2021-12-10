import { Component, EventEmitter, Inject, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { CommonOrder } from 'src/app/main/common/common-order';
import { ProviceVnService } from 'src/app/main/common/provice-vn.service';
import { Product } from 'src/app/main/product/model/product';

import { OrderComponent } from '../../container/order.component';
import { Order } from '../../model/order';
import { OrderDetail } from '../../model/order-detail';
import { OrderService } from '../../service/order.service';

@Component({
  selector: 'app-order-form',
  templateUrl: './order-form.component.html',
  styles: [
    `
      .form-order-admin input,
      textarea {
        font-weight: 500;
      }

      .row--order {
        margin-bottom: 15px;
      }

      .row--order:last-child {
        margin-bottom: 0;
        font-size: 16px;
      }

      .total-price {
        color: #fea023;
        font-size: 18px;
      }

      .color-group {
        color: #1890ff;
      }
    `
  ]
})
export class OrderFormComponent implements OnInit {
  @Output() mySubmit = new EventEmitter();
  form: FormGroup;

  insuranceDate: string;

  order = new Order();

  orderDetail: OrderDetail;

  commonOrder = new CommonOrder();

  lable_button = 'Đang bảo hành';

  icon_lable = 'setting';

  constructor(
    private orderService: OrderService,
    public proviceSerVice: ProviceVnService,
    @Inject(OrderComponent) public parent: OrderComponent
  ) {}

  ngOnInit(): void {}

  showOrderDetial(data: OrderDetail) {
    this.orderDetail = data;
  }

  getReturnDateProduct(date: string) {
    let dayNow = new Date();

    let dateComp = new Date(date);
    const day = dayNow.getDate() - dateComp.getDate();
    return day <= 0
      ? { style: 'color: #FF4D4F', value: 'Hết hạn đổi trả' }
      : day <= 5
      ? { style: 'color: #ffc107', value: day }
      : { style: 'color: #1890ff', value: day };
  }

  getInsuranceDateProduct(d: string) {
    let dateComp = new Date(d);
    dateComp.setDate(dateComp.getDate() + 365 * (this.orderDetail.product.productsDetail.warrantyPeriod / 12));

    this.insuranceDate = this.convertDateToString(dateComp);
  }

  convertDateToString(date: Date) {
    return `${date.getDate() < 10 ? `0${date.getDate()}` : date.getDate()}-${
      date.getMonth() + 1 < 10 ? `0${date.getMonth() + 1}` : date.getMonth() + 1
    }-${date.getFullYear()}`;
  }

  confirmOrder(data: Order) {
    data.received = 1;
    this.updateOrder(data);
  }

  updateOrder(data: Order) {  
    this.orderService.update(data).subscribe(
      (res: any) => {
        this.mySubmit.emit();
      },
      err => {
        console.log(err);
      }
    );
  }

  changeOrderDetail(data: Order, oDetail: OrderDetail) {
    data.orderDetails.map((value: any) => {
      if (value.id == oDetail.id) {
        value.status = 0;
        this.orderService.updateOrderDetail(value).subscribe(
          (res: any) => {},
          err => {
            console.log(err);
          }
        );
      }
    });
  }
}
