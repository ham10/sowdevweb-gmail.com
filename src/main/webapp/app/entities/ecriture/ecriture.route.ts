import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEcriture, Ecriture } from 'app/shared/model/ecriture.model';
import { EcritureService } from './ecriture.service';
import { EcritureComponent } from './ecriture.component';
import { EcritureDetailComponent } from './ecriture-detail.component';
import { EcritureUpdateComponent } from './ecriture-update.component';

@Injectable({ providedIn: 'root' })
export class EcritureResolve implements Resolve<IEcriture> {
  constructor(private service: EcritureService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEcriture> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ecriture: HttpResponse<Ecriture>) => {
          if (ecriture.body) {
            return of(ecriture.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Ecriture());
  }
}

export const ecritureRoute: Routes = [
  {
    path: '',
    component: EcritureComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.ecriture.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EcritureDetailComponent,
    resolve: {
      ecriture: EcritureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.ecriture.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EcritureUpdateComponent,
    resolve: {
      ecriture: EcritureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.ecriture.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EcritureUpdateComponent,
    resolve: {
      ecriture: EcritureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.ecriture.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
