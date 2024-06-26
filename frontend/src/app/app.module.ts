import {LOCALE_ID, NgModule} from '@angular/core';
import localeRu from '@angular/common/locales/ru';
import {CommonModule, registerLocaleData} from '@angular/common';
import {MainComponent} from "./core/components/main/main.component";
import {CoreModule} from "./core/core.module";
import {AppRoutingModule} from "./app-routing.module";
import {BrowserModule} from "@angular/platform-browser";
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
import {provideMomentDateAdapter} from "@angular/material-moment-adapter";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {ApiPrefixInterceptor} from "./core/interceptors/api-prefix.interceptor";
import {ApiErrorInterceptor} from "./core/interceptors/api-error.interceptor";
import {ToastrModule} from "ngx-toastr";
import {strings as russianStrings} from "ngx-timeago/language-strings/ru";
import {MAT_FORM_FIELD_DEFAULT_OPTIONS} from "@angular/material/form-field";

registerLocaleData(localeRu);

@NgModule({
  declarations: [
    MainComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    CoreModule,
    AppRoutingModule,
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
    ToastrModule.forRoot()
  ],
  bootstrap: [MainComponent],
  providers: [
    provideAnimationsAsync(),
    provideMomentDateAdapter(),
    {provide: HTTP_INTERCEPTORS, useClass: ApiPrefixInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ApiErrorInterceptor, multi: true},
    {provide: LOCALE_ID, useValue: 'ru'},
    {
      provide: MAT_FORM_FIELD_DEFAULT_OPTIONS,
      useValue: {subscriptSizing: 'dynamic'}
    }

  ]
})
export class AppModule {
  constructor(intl: TimeagoIntl) {
    intl.strings = russianStrings;
  }
}
