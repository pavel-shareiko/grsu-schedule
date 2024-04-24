import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MainComponent} from "./core/components/main/main.component";
import {CoreModule} from "./core/core.module";
import {AppRoutingModule} from "./app-routing.module";
import {BrowserModule} from "@angular/platform-browser";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    MainComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    CoreModule,
    AppRoutingModule,
    NgbModule
  ],
  bootstrap: [MainComponent]
})
export class AppModule {
}
