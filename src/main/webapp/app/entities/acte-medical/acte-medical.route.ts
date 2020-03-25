import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IActeMedical, ActeMedical } from 'app/shared/model/acte-medical.model';
import { ActeMedicalService } from './acte-medical.service';
import { ActeMedicalComponent } from './acte-medical.component';
import { ActeMedicalDetailComponent } from './acte-medical-detail.component';
import { ActeMedicalUpdateComponent } from './acte-medical-update.component';

@Injectable({ providedIn: 'root' })
export class ActeMedicalResolve implements Resolve<IActeMedical> {
  constructor(private service: ActeMedicalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IActeMedical> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((acteMedical: HttpResponse<ActeMedical>) => {
          if (acteMedical.body) {
            return of(acteMedical.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ActeMedical());
  }
}

export const acteMedicalRoute: Routes = [
  {
    path: '',
    component: ActeMedicalComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.acteMedical.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ActeMedicalDetailComponent,
    resolve: {
      acteMedical: ActeMedicalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.acteMedical.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ActeMedicalUpdateComponent,
    resolve: {
      acteMedical: ActeMedicalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.acteMedical.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ActeMedicalUpdateComponent,
    resolve: {
      acteMedical: ActeMedicalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.acteMedical.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
