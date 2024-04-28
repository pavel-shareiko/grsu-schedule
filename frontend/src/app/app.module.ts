import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MainComponent} from "./core/components/main/main.component";
import {CoreModule} from "./core/core.module";
import {AppRoutingModule} from "./app-routing.module";
import {BrowserModule} from "@angular/platform-browser";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {SidebarComponent} from "./core/components/sidebar/sidebar.component";
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

@NgModule({
  declarations: [
    MainComponent
  ],
    imports: [
        CommonModule,
        BrowserModule,
        CoreModule,
        AppRoutingModule,
        NgbModule,
        SidebarComponent
    ],
  bootstrap: [MainComponent],
  providers: [
    provideAnimationsAsync()
  ]
})
export class AppModule {
}
