import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypePatient, TypePatient } from 'app/shared/model/type-patient.model';
import { TypePatientService } from './type-patient.service';
import { TypePatientComponent } from './type-patient.component';
import { TypePatientDetailComponent } from './type-patient-detail.component';
import { TypePatientUpdateComponent } from './type-patient-update.component';

@Injectable({ providedIn: 'root' })
export class TypePatientResolve implements Resolve<ITypePatient> {
  constructor(private service: TypePatientService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypePatient> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typePatient: HttpResponse<TypePatient>) => {
          if (typePatient.body) {
            return of(typePatient.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypePatient());
  }
}

export const typePatientRoute: Routes = [
  {
    path: '',
    component: TypePatientComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePatient.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypePatientDetailComponent,
    resolve: {
      typePatient: TypePatientResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePatient.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypePatientUpdateComponent,
    resolve: {
      typePatient: TypePatientResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePatient.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypePatientUpdateComponent,
    resolve: {
      typePatient: TypePatientResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePatient.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
