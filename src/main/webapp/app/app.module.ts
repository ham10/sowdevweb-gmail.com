import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { HpdSharedModule } from 'app/shared/shared.module';
import { HpdCoreModule } from 'app/core/core.module';
import { HpdAppRoutingModule } from './app-routing.module';
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { ConnexionComponent } from './connexion/connexion.component';
import { MenuModule } from 'app/menu/menu.module';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
// import {HpdEntityModule} from "app/entities/entity.module";

@NgModule({
  imports: [BrowserModule, BrowserAnimationsModule, HpdSharedModule, HpdCoreModule, HpdAppRoutingModule, MenuModule, FontAwesomeModule
  ],
  declarations: [
    MainComponent,
    NavbarComponent,
    ErrorComponent,
    PageRibbonComponent,
    ActiveMenuDirective,
    FooterComponent,
    ConnexionComponent
  ],

  bootstrap: [MainComponent]
})
export class HpdAppModule {}
