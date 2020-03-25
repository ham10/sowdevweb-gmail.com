import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICivilite, Civilite } from 'app/shared/model/civilite.model';
import { CiviliteService } from './civilite.service';
import { CiviliteComponent } from './civilite.component';
import { CiviliteDetailComponent } from './civilite-detail.component';
import { CiviliteUpdateComponent } from './civilite-update.component';

@Injectable({ providedIn: 'root' })
export class CiviliteResolve implements Resolve<ICivilite> {
  constructor(private service: CiviliteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICivilite> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((civilite: HttpResponse<Civilite>) => {
          if (civilite.body) {
            return of(civilite.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Civilite());
  }
}

export const civiliteRoute: Routes = [
  {
    path: '',
    component: CiviliteComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.civilite.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CiviliteDetailComponent,
    resolve: {
      civilite: CiviliteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.civilite.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CiviliteUpdateComponent,
    resolve: {
      civilite: CiviliteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.civilite.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CiviliteUpdateComponent,
    resolve: {
      civilite: CiviliteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.civilite.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
