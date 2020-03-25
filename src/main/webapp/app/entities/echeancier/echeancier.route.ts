import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEcheancier, Echeancier } from 'app/shared/model/echeancier.model';
import { EcheancierService } from './echeancier.service';
import { EcheancierComponent } from './echeancier.component';
import { EcheancierDetailComponent } from './echeancier-detail.component';
import { EcheancierUpdateComponent } from './echeancier-update.component';

@Injectable({ providedIn: 'root' })
export class EcheancierResolve implements Resolve<IEcheancier> {
  constructor(private service: EcheancierService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEcheancier> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((echeancier: HttpResponse<Echeancier>) => {
          if (echeancier.body) {
            return of(echeancier.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Echeancier());
  }
}

export const echeancierRoute: Routes = [
  {
    path: '',
    component: EcheancierComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.echeancier.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EcheancierDetailComponent,
    resolve: {
      echeancier: EcheancierResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.echeancier.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EcheancierUpdateComponent,
    resolve: {
      echeancier: EcheancierResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.echeancier.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EcheancierUpdateComponent,
    resolve: {
      echeancier: EcheancierResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.echeancier.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
