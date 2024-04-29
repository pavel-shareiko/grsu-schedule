import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MainComponent} from "./core/components/main/main.component";
import {CoreModule} from "./core/core.module";
import {AppRoutingModule} from "./app-routing.module";
import {BrowserModule} from "@angular/platform-browser";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {TimeagoCustomFormatter, TimeagoFormatter, TimeagoIntl, TimeagoModule} from "ngx-timeago";
import {MatToolbar} from "@angular/material/toolbar";
import {MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatSidenav, MatSidenavContainer, MatSidenavContent} from "@angular/material/sidenav";
import {MatListItem, MatNavList} from "@angular/material/list";
import {SidebarNavigationComponent} from "./core/components/sidebar/sidebar-navigation/sidebar-navigation.component";
import {SidebarHeaderComponent} from "./core/components/sidebar/sidebar-header/sidebar-header.component";
import {SidebarFooterComponent} from "./core/components/sidebar/sidebar-footer/sidebar-footer.component";

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
    TimeagoModule.forRoot({
      intl: {provide: TimeagoIntl, useClass: TimeagoIntl},
      formatter: {provide: TimeagoFormatter, useClass: TimeagoCustomFormatter},
    }),
    MatToolbar,
    MatIconButton,
    MatIcon,
    MatSidenavContent,
    MatSidenavContainer,
    MatSidenav,
    MatNavList,
    MatListItem,
    SidebarNavigationComponent,
    SidebarHeaderComponent,
    SidebarFooterComponent,
  ],
  bootstrap: [MainComponent],
  providers: [
    provideAnimationsAsync()
  ]
})
export class AppModule {
}
