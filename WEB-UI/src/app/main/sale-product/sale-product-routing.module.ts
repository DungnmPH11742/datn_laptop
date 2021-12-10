import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SaleProductComponent} from './container/sale-product.component'
const routes: Routes = [{path:'', component:SaleProductComponent}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SaleProductRoutingModule { }
