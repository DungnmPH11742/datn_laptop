import { NgModule, Type } from '@angular/core';
import { SharedModule } from '@shared';

// dashboard pages
import { DashboardComponent } from './dashboard/dashboard.component';
// single pages
import { MainRoutingModule } from './main-routing.module';
import { CallbackComponent } from './passport/callback.component';
import { UserLockComponent } from './passport/lock/lock.component';
// passport pages
import { UserLoginComponent } from './passport/login/login.component';
import { UserRegisterResultComponent } from './passport/register-result/register-result.component';
import { UserRegisterComponent } from './passport/register/register.component';

const COMPONENTS: Array<Type<void>> = [
  DashboardComponent,
  // passport pages
  UserLoginComponent,
  UserRegisterComponent,
  UserRegisterResultComponent,
  // single pages
  CallbackComponent,
  UserLockComponent
];

@NgModule({
  imports: [SharedModule, MainRoutingModule],
  declarations: COMPONENTS
})
export class MainModule {}
