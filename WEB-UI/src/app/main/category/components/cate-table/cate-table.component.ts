import { Component, OnInit } from '@angular/core';

import { Category } from '../../model/category';
import { CategoryService } from '../../service/category.service';

@Component({
  selector: 'app-cate-table',
  templateUrl: './cate-table.component.html',
  styles: []
})
export class CateTableComponent implements OnInit {
  listOfData: Category[];

  pageSize = 5;

  page = 1;

  constructor(private cateService: CategoryService) { }

  ngOnInit(): void {
    this.findAll();
  }

  findAll() {
    this.cateService.findAll().subscribe(
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
