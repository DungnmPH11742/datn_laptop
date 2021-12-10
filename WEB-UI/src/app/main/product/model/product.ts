import { Category } from '../../category/model/category';
import { ProductDetail } from './product-detail';

export class Product {
  id: string;

  name: string;

  dateOn: string;

  typeOfItem: number;

  inputPrice: number;

  outputPrice: number;

  quantity: number;

  mass: number;

  unit: string;

  releaseDate: string;

  dateOfManufacture: string;

  imgUrl: string;

  saleProduct: {};

  productsDetail = new ProductDetail();

  category: Category;

  active: boolean;
}
