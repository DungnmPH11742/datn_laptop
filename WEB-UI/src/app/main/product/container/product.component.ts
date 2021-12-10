import { Component, OnInit, ViewChild } from '@angular/core';

import { ProductFormComponent } from '../components/product-form/product-form.component';
import { ProductTableComponent } from '../components/product-table/product-table.component';
import { Product } from '../model/product';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styles: []
})
export class ProductComponent implements OnInit {
  @ViewChild(ProductFormComponent) formChild: ProductFormComponent;
  @ViewChild(ProductTableComponent) tableChild: ProductTableComponent;
  constructor() { }

  ngOnInit(): void { }

  showDetail(data: Product) {
    this.formChild.showDetail(data);
  }

  changeListProduct() {
    this.tableChild.findAll();
  }
}
