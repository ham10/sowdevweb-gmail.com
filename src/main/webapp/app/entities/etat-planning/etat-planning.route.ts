import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEtatPlanning, EtatPlanning } from 'app/shared/model/etat-planning.model';
import { EtatPlanningService } from './etat-planning.service';
import { EtatPlanningComponent } from './etat-planning.component';
import { EtatPlanningDetailComponent } from './etat-planning-detail.component';
import { EtatPlanningUpdateComponent } from './etat-planning-update.component';

@Injectable({ providedIn: 'root' })
export class EtatPlanningResolve implements Resolve<IEtatPlanning> {
  constructor(private service: EtatPlanningService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEtatPlanning> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((etatPlanning: HttpResponse<EtatPlanning>) => {
          if (etatPlanning.body) {
            return of(etatPlanning.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EtatPlanning());
  }
}

export const etatPlanningRoute: Routes = [
  {
    path: '',
    component: EtatPlanningComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatPlanning.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EtatPlanningDetailComponent,
    resolve: {
      etatPlanning: EtatPlanningResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatPlanning.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EtatPlanningUpdateComponent,
    resolve: {
      etatPlanning: EtatPlanningResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatPlanning.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EtatPlanningUpdateComponent,
    resolve: {
      etatPlanning: EtatPlanningResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatPlanning.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
