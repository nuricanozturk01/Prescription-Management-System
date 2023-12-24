import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {NgOptimizedImage} from "@angular/common";
import {PrescriptionComponent} from './prescription/prescription.component';
import {RouterModule} from "@angular/router";
import {AppRoutingModule} from "./app-routing.module";
import { LoginComponent } from './login/login.component';


@NgModule({
  declarations: [
    AppComponent,
    PrescriptionComponent,
    LoginComponent
  ],
  exports: [RouterModule
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    NgbModule,
    NgOptimizedImage
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
