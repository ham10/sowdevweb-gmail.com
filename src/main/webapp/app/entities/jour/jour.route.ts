import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IJour, Jour } from 'app/shared/model/jour.model';
import { JourService } from './jour.service';
import { JourComponent } from './jour.component';
import { JourDetailComponent } from './jour-detail.component';
import { JourUpdateComponent } from './jour-update.component';

@Injectable({ providedIn: 'root' })
export class JourResolve implements Resolve<IJour> {
  constructor(private service: JourService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJour> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((jour: HttpResponse<Jour>) => {
          if (jour.body) {
            return of(jour.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Jour());
  }
}

export const jourRoute: Routes = [
  {
    path: '',
    component: JourComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.jour.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: JourDetailComponent,
    resolve: {
      jour: JourResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.jour.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: JourUpdateComponent,
    resolve: {
      jour: JourResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.jour.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: JourUpdateComponent,
    resolve: {
      jour: JourResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.jour.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
