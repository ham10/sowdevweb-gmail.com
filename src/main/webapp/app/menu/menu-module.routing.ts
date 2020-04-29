import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeMenuComponent } from 'app/menu/home-menu/home-menu.component';

const routes: Routes = [
  {
    path: '',
    component: HomeMenuComponent,
    children: [
      {
        path: 'hospitalisation',
        loadChildren: () => import('../entities/hospitalisation/hospitalisation.module').then(m => m.HpdHospitalisationModule)
      },
      {
        path: 'patient',
        loadChildren: () => import('../entities/patient/patient.module').then(m => m.HpdPatientModule)
      },
      {
        path: 'rdv',
        loadChildren: () => import('../entities/rdv/rdv.module').then(m => m.HpdRDVModule)
      },
      {
        path: 'planning',
        loadChildren: () => import('../entities/planning/planning.module').then(m => m.HpdPlanningModule)
      },
      {
        path: 'medecin',
        loadChildren: () => import('../entities/medecin/medecin.module').then(m => m.HpdMedecinModule)
      },
      {
        path: 'type-planning',
        loadChildren: () => import('../entities/type-planning/type-planning.module').then(m => m.HpdTypePlanningModule)
      }
    ]
  }
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MenuRoutingModule {}
