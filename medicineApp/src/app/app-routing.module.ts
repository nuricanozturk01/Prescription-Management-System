import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PrescriptionComponent} from "./prescription/prescription.component";
import {LoginComponent} from "./login/login.component";


const routes: Routes = [
  {path: 'prescription', component: PrescriptionComponent, pathMatch: 'full'},
  {path: 'login', component: LoginComponent, pathMatch: 'full'},
  {path: '', redirectTo: '/login', pathMatch: 'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
