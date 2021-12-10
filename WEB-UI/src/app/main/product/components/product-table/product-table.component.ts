import { Component, Inject, OnInit } from '@angular/core';

import { ProductComponent } from '../../container/product.component';
import { Product } from '../../model/product';
import { ProductService } from '../../service/product.service';

@Component({
  selector: 'app-product-table',
  templateUrl: './product-table.component.html',
  styles: []
})
export class ProductTableComponent implements OnInit {
  page = 1;
  pageSize = 5;
  listOfData: Product[] = [];
  constructor(private productService: ProductService, @Inject(ProductComponent) public parent: ProductComponent) { }

  ngOnInit(): void {
    this.findAll();
  }

  findAll() {
    this.productService.findAll().subscribe(
      (res: any) => {
        console.log(res);
        this.listOfData = res;
      },
      err => {
        console.log(err);
      }
    );
  }
}
