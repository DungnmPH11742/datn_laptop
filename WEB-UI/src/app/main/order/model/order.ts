import { Account } from '../../account/model/account';
import { OrderDetail } from './order-detail';

export class Order {
  id: number;

  orderCode: string;

  account = new Account();

  voucher: any;

  orderDate: string;

  phoneNumber: string;

  address = new String();

  description: string;

  completionDate: string;

  paymentStatus: boolean;

  paymentMethods: number;

  authenticator: string;

  received: number;

  orderDetails = [new OrderDetail()];
}
