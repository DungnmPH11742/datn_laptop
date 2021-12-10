import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Category } from 'src/app/main/category/model/category';
import { CategoryService } from 'src/app/main/category/service/category.service';
import { CommonProduct } from 'src/app/main/common/common-product';
import { Sale } from 'src/app/main/sale-product/model/sale';
import { SaleService } from 'src/app/main/sale-product/service/sale.service';
import { UploadFileService } from 'src/app/main/upload-file/upload-file.service';

import { Product } from '../../model/product';
import { ProductService } from '../../service/product.service';

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styles: [
    `
      nz-form-control {
        margin: 0 25px;
      }
      .content-p {
        float: left;
        width: 100%;
      }
      .img-left {
        width: 20%;
        float: left;
      }
      .right-content {
        width: 80%;
        float: right;
      }

      .upload-btn-wrapper {
        position: relative;
        overflow: hidden;
        display: inline-block;
      }

      .boder-upload {
        height: 250px;
        width: 250px;
        border: 1px solid #ccc;
        border-radius: 1.5%;
      }

      .boder-upload i {
        font-size: 48px;
      }

      .center-icon-add {
        font-size: 34px;
        font-weight: 700;
      }

      .center {
        display: flex;
        justify-content: center;
        align-items: center;
      }

      .upload-btn-wrapper input[type='file'] {
        height: 250px;
        width: 250px;
        font-size: 100px;
        position: absolute;
        left: 0;
        top: 0;
        opacity: 0;
      }

      .img-default {
        width: 100%;
      }
    `
  ]
})
export class ProductFormComponent implements OnInit {
  @Output() mySubmit = new EventEmitter();
  form: FormGroup;

  product = new Product();

  commonP = new CommonProduct();

  file: File;

  img: string = '';

  listCate: Category[];

  listSale: Sale[];

  nodeCateList: any = [];

  nodeCate: Category[];

  formatterPercent = (value: number): string => `${value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.')} đ`;

  formatterWarranty = (value: number): string => `${value} tháng`;

  formatterMass = (value: number): string => `${value} Kg`;

  constructor(
    private fb: FormBuilder,
    private cateService: CategoryService,
    public prodService: ProductService,
    private saleService: SaleService,
    private uploadService: UploadFileService
  ) { }

  createForm() {
    this.form = this.fb.group({
      id: '',
      name: [''],
      dateOn: [new Date()],
      typeOfItem: [1],
      inputPrice: [0],
      outputPrice: [0],
      quantity: [0],
      mass: [0],
      unit: [''],
      imgUrl: '',
      releaseDate: [''],
      dateOfManufacture: [''],
      saleProduct: [''],
      productsDetail: this.fb.group({
        id: '',
        producer: '',
        cpu: '',
        color: '',
        ram: '',
        hardDrive: '',
        webcam: '',
        vga: '',
        operatingSystem: '',
        power: '',
        displaySize: '',
        screenRatio: '',
        scanFrequency: '',
        backgroundPanels: '',
        resolution: '',
        contrast: '',
        responseTime: '',
        brightness: '',
        viewScreenshots: '',
        warrantyPeriod: 0,
        accessoriesIncluded: '',
        seeMore: ''
      }),
      category: {
        id: 0,
        name: '',
        actived: true,
        parentId: 0
      },
      active: true
    });
    this.form.get('active')?.setValue(true);
  }
  ngOnInit(): void {
    this.createForm();
    this.getListCate();
    this.getNodeCate();
    this.getListSale();
    this.getListNodeCate();
  }

  showDetail(data: any) {
    this.form.setValue(data);
    this.form.get('active')?.setValue(`${data.active}`);
    this.form.get('saleProduct')?.setValue(data.saleProduct?.promotionType);
    console.log(this.form.value);
  }

  convertDateToString(date: Date) {
    return `${date.getFullYear()}-${date.getMonth() + 1 < 10 ? `0${date.getMonth() + 1}` : date.getMonth() + 1}-${date.getDate() < 10 ? `0${date.getDate()}` : date.getDate()
      }`;
  }

  convertDateToYear(date: Date) {
    return `${date.getFullYear()} `;
  }

  convertToModel(form: any) {
    this.product = this.form.value;
    this.product.dateOn = this.convertDateToString(new Date());
    this.product.releaseDate = this.convertDateToYear(form.get('releaseDate').value);
    this.product.dateOfManufacture = this.convertDateToString(form.get('dateOfManufacture').value);
    return this.product;
  }

  selectFile(event: any) {
    this.file = event.target.files.item(0);
    this.img = URL.createObjectURL(event.target.files[0]);
  }

  getListCate() {
    this.cateService.findAllByParentId(this.form.get('typeOfItem')?.value).subscribe(
      (res: any) => {
        this.listCate = res;
      },
      err => {
        console.log(err);
      }
    );
  }

  getNodeCate() {
    this.cateService.findNodeCate().subscribe((data: any) => {
      return (this.nodeCate = data);
    });
  }

  getListNodeCate() {
    this.nodeCateList = [];
    this.cateService.findAll().subscribe((data: any) => {
      data.map((e: any) => {
        if (e.parentId === this.form.get('typeOfItem')?.value) {
          this.nodeCateList.push({
            title: `${e.name} `,
            key: e,
            children: []
          });
        }
      });

      for (let i = 0; i < this.nodeCateList.length; i++) {
        data.map((e_child: any) => {
          if (e_child.parentId == this.nodeCateList[i].key.id) {
            this.nodeCateList[i].children.push({
              title: `${e_child.name} `,
              key: e_child
            });
          }
        });
      }
    });
  }

  getListSale() {
    this.saleService.findAll().subscribe((data: any) => {
      this.listSale = data;
    });
  }

  submitForm() {
    this.uploadService.uploadFile(this.file).subscribe(
      (res: any) => {
        console.log(res);
        this.saveProduct(res.fileViewUri);
      },
      err => {
        console.log(err);
      }
    );
  }

  saveProduct(url: any) {
    this.convertToModel(this.form);
    this.product.imgUrl = url;
    this.prodService.save(this.product).subscribe(
      (res: any) => {
        this.mySubmit.emit();
        console.log(res);
      },
      err => {
        console.log(err);
      }
    );
  }

  updateProduct() {
    this.prodService.update(this.convertToModel(this.form)).subscribe(
      (res: any) => {
        this.mySubmit.emit();
        console.log(res);
      },
      err => {
        console.log(err);
      }
    );
  }
}
