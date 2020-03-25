import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDosMedical, DosMedical } from 'app/shared/model/dos-medical.model';
import { DosMedicalService } from './dos-medical.service';
import { DosMedicalComponent } from './dos-medical.component';
import { DosMedicalDetailComponent } from './dos-medical-detail.component';
import { DosMedicalUpdateComponent } from './dos-medical-update.component';

@Injectable({ providedIn: 'root' })
export class DosMedicalResolve implements Resolve<IDosMedical> {
  constructor(private service: DosMedicalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDosMedical> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dosMedical: HttpResponse<DosMedical>) => {
          if (dosMedical.body) {
            return of(dosMedical.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DosMedical());
  }
}

export const dosMedicalRoute: Routes = [
  {
    path: '',
    component: DosMedicalComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.dosMedical.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DosMedicalDetailComponent,
    resolve: {
      dosMedical: DosMedicalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.dosMedical.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DosMedicalUpdateComponent,
    resolve: {
      dosMedical: DosMedicalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.dosMedical.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DosMedicalUpdateComponent,
    resolve: {
      dosMedical: DosMedicalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.dosMedical.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
