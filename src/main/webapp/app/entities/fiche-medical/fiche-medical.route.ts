import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFicheMedical, FicheMedical } from 'app/shared/model/fiche-medical.model';
import { FicheMedicalService } from './fiche-medical.service';
import { FicheMedicalComponent } from './fiche-medical.component';
import { FicheMedicalDetailComponent } from './fiche-medical-detail.component';
import { FicheMedicalUpdateComponent } from './fiche-medical-update.component';

@Injectable({ providedIn: 'root' })
export class FicheMedicalResolve implements Resolve<IFicheMedical> {
  constructor(private service: FicheMedicalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFicheMedical> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ficheMedical: HttpResponse<FicheMedical>) => {
          if (ficheMedical.body) {
            return of(ficheMedical.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FicheMedical());
  }
}

export const ficheMedicalRoute: Routes = [
  {
    path: '',
    component: FicheMedicalComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.ficheMedical.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FicheMedicalDetailComponent,
    resolve: {
      ficheMedical: FicheMedicalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.ficheMedical.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FicheMedicalUpdateComponent,
    resolve: {
      ficheMedical: FicheMedicalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.ficheMedical.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FicheMedicalUpdateComponent,
    resolve: {
      ficheMedical: FicheMedicalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.ficheMedical.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
