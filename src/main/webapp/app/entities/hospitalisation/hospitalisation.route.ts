import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IHospitalisation, Hospitalisation } from 'app/shared/model/hospitalisation.model';
import { HospitalisationService } from './hospitalisation.service';
import { HospitalisationComponent } from './hospitalisation.component';
import { HospitalisationDetailComponent } from './hospitalisation-detail.component';
import { HospitalisationUpdateComponent } from './hospitalisation-update.component';

@Injectable({ providedIn: 'root' })
export class HospitalisationResolve implements Resolve<IHospitalisation> {
  constructor(private service: HospitalisationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHospitalisation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((hospitalisation: HttpResponse<Hospitalisation>) => {
          if (hospitalisation.body) {
            return of(hospitalisation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Hospitalisation());
  }
}

export const hospitalisationRoute: Routes = [
  {
    path: '',
    component: HospitalisationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.hospitalisation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: HospitalisationDetailComponent,
    resolve: {
      hospitalisation: HospitalisationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.hospitalisation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: HospitalisationUpdateComponent,
    resolve: {
      hospitalisation: HospitalisationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.hospitalisation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: HospitalisationUpdateComponent,
    resolve: {
      hospitalisation: HospitalisationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.hospitalisation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
