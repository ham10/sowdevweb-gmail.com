import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarMenuComponent } from './navbar-menu/navbar-menu.component';
import { SidebarMenuComponent } from './sidebar-menu/sidebar-menu.component';
import { FooterMenuComponent } from './footer-menu/footer-menu.component';
import { MenuRoutingModule } from 'app/menu/menu-module.routing';
import { HomeMenuComponent } from './home-menu/home-menu.component';
import { HpdHospitalisationModule } from 'app/entities/hospitalisation/hospitalisation.module';
import { HpdPatientModule } from 'app/entities/patient/patient.module';
import { HpdRDVModule } from 'app/entities/rdv/rdv.module';
import { HpdPlanningModule } from 'app/entities/planning/planning.module';
// import {HpdHospitalisationModule} from "app/entities/hospitalisation/hospitalisation.module";

@NgModule({
  declarations: [NavbarMenuComponent, SidebarMenuComponent, FooterMenuComponent, HomeMenuComponent],
  imports: [CommonModule, MenuRoutingModule, HpdHospitalisationModule, HpdPatientModule, HpdRDVModule, HpdPlanningModule],
  // bootstrap: [HomeMenuComponent],
  exports: [HomeMenuComponent]
})
export class MenuModule {}
