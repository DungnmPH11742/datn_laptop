import { Component, OnInit, ViewChild } from '@angular/core';
import { SaleProductFormComponent } from '../components/sale-product-form/sale-product-form.component';
import { SaleProductTableComponent } from '../components/sale-product-table/sale-product-table.component';
import { Sale } from '../model/sale';

@Component({
  selector: 'app-sale-product',
  templateUrl: './sale-product.component.html',
  styles: []
})
export class SaleProductComponent implements OnInit {
  @ViewChild(SaleProductFormComponent) formChild:SaleProductFormComponent;
  @ViewChild(SaleProductTableComponent) tableChild: SaleProductTableComponent;

  constructor() { }

  ngOnInit(): void {
  }

  showSaleDetail(data: Sale){
    this.formChild.showSaleDetail(data);
  }

  findAll(){
    this.tableChild.findAll();
  }



}
