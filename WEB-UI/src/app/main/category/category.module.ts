import { NgModule, Type } from '@angular/core';
import { SharedModule } from '@shared';

import { CategoryRoutingModule } from './category-routing.module';
import { CateFormComponent } from './components/cate-form/cate-form.component';
import { CateTableComponent } from './components/cate-table/cate-table.component';
import { CategoryComponent } from './container/category.component';

@NgModule({
  declarations: [CategoryComponent, CateTableComponent, CateFormComponent],
  imports: [SharedModule, CategoryRoutingModule]
})
export class CategoryModule { }
