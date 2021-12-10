import { Product } from '../../product/model/product';

export class OrderDetail {
  id: number;

  idOrder: number;

  product = new Product();

  voucher: {};

  price: number;

  quantity: number;

  status: number;
}
