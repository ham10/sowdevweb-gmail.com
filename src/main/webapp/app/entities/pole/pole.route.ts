import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPole, Pole } from 'app/shared/model/pole.model';
import { PoleService } from './pole.service';
import { PoleComponent } from './pole.component';
import { PoleDetailComponent } from './pole-detail.component';
import { PoleUpdateComponent } from './pole-update.component';

@Injectable({ providedIn: 'root' })
export class PoleResolve implements Resolve<IPole> {
  constructor(private service: PoleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPole> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pole: HttpResponse<Pole>) => {
          if (pole.body) {
            return of(pole.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Pole());
  }
}

export const poleRoute: Routes = [
  {
    path: '',
    component: PoleComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.pole.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PoleDetailComponent,
    resolve: {
      pole: PoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.pole.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PoleUpdateComponent,
    resolve: {
      pole: PoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.pole.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PoleUpdateComponent,
    resolve: {
      pole: PoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.pole.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
