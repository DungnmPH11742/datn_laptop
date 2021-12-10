import { Component, Inject, OnInit } from '@angular/core';
import { SaleProductComponent } from '../../container/sale-product.component';
import { Sale } from '../../model/sale';

import { NzModalService } from 'ng-zorro-antd/modal';
import { SaleService } from '../../service/sale.service';

@Component({
  selector: 'app-sale-product-table',
  templateUrl: './sale-product-table.component.html',
  styles: [
  ]
})
export class SaleProductTableComponent implements OnInit {

  listSale: Sale[] = [];

  page = 1;

  pageSize = 5;

  constructor( private modal: NzModalService, private saleService: SaleService, @Inject(SaleProductComponent,) public parent: SaleProductComponent) { }

 
  ngOnInit(): void {
    this.findAll();
  }

  findAll(){
    this.saleService.findAll().subscribe((res:any) => {
        this.listSale = res;
    }, errorr => {
      console.log(errorr);
    });
  }

  changeStatus(sale:Sale){
    this.listSale = this.listSale.map((value: Sale) => {
      if(value.saleCode == sale.saleCode){
        sale.status = sale.status == 1 ? 0 : 1;
        this.saleService.updateSale(sale).subscribe((sale:any) => {
          alert("Cập nhật trạng thái thành công!!");
            
        },err => {
            console.log(err);
            alert("Cập nhật trạng thái thất bại");
        });
      }
      return value;
    })
  }

  delete(saleCode: String){
    this.modal.confirm({
      nzTitle: 'Bạn có chắc chắn muốn xóa không??',
      nzOkText: 'Xác nhận',
      nzOkType: 'primary',
      nzOkDanger: true,
      nzOnOk: () =>{
        this.saleService.deleteSale(saleCode).subscribe(res =>{
          this.findAll();
          alert('Xóa thành công');
        },err => {
          alert('Xóa thất bại');
          console.log(err);
        });
      },
      nzCancelText: 'Không',
      
      nzOnCancel: () => console.log('Cancel')
    });
  }


}
