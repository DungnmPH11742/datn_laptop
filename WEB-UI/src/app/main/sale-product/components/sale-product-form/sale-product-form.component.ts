import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SaleProductComponent } from '../../container/sale-product.component';
import { Sale } from '../../model/sale';
import { SaleService } from '../../service/sale.service';

@Component({
  selector: 'app-sale-product-form',
  templateUrl: './sale-product-form.component.html',
  styles: [
  ]
})
export class SaleProductFormComponent implements OnInit {

  form : FormGroup;
  saleProduct = new Sale();
  constructor(
    private fb:FormBuilder,
    private saleService:SaleService,
    @Inject(SaleProductComponent) private parent: SaleProductComponent
  ) { }

  ngOnInit(): void {
    this.createForm();
  }

  createForm(){
    this.form = this.fb.group({
        saleCode: [''],
        promotionType: ['',[Validators.required,Validators.minLength(5), Validators.maxLength(255)]],
        dateOn: [null,[Validators.required]],
        dateOff: [null,[Validators.required]],
        promotion: [0, [Validators.required,Validators.min(0),Validators.max(100)]],
        quantity: [0,[Validators.required, Validators.min(0)]],
        status: [0]

    });
  }

  showSaleDetail(data: any){
    this.form.setValue(data);
    this.form.get('status')?.setValue(`${data.status}`);
  }

  cancelSale(){
    this.createForm();
  }

  createSale(){
      this.saleProduct = this.form.value;
      if(this.saleProduct.saleCode == ''){

        this.saleService.createSale(this.saleProduct).subscribe(     (res: any) =>{
          this.cancelSale();
          alert("Thêm thành công!!");
          this.parent.findAll();
        },err =>{
        console.log(err);
        alert("Thêm thất bại");
        })

      }else{
        this.saleService.updateSale(this.saleProduct).subscribe((res: any) =>{
          this.cancelSale();
          alert("Update thành công!!");
          this.parent.findAll();
      },err =>{
        console.log(err);
        alert("Update thất bại");
      })
      }
  }


}
