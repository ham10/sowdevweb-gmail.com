import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IJourFerie, JourFerie } from 'app/shared/model/jour-ferie.model';
import { JourFerieService } from './jour-ferie.service';
import { JourFerieComponent } from './jour-ferie.component';
import { JourFerieDetailComponent } from './jour-ferie-detail.component';
import { JourFerieUpdateComponent } from './jour-ferie-update.component';

@Injectable({ providedIn: 'root' })
export class JourFerieResolve implements Resolve<IJourFerie> {
  constructor(private service: JourFerieService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJourFerie> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((jourFerie: HttpResponse<JourFerie>) => {
          if (jourFerie.body) {
            return of(jourFerie.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new JourFerie());
  }
}

export const jourFerieRoute: Routes = [
  {
    path: '',
    component: JourFerieComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.jourFerie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: JourFerieDetailComponent,
    resolve: {
      jourFerie: JourFerieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.jourFerie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: JourFerieUpdateComponent,
    resolve: {
      jourFerie: JourFerieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.jourFerie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: JourFerieUpdateComponent,
    resolve: {
      jourFerie: JourFerieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.jourFerie.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
