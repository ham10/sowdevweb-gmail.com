import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILit, Lit } from 'app/shared/model/lit.model';
import { LitService } from './lit.service';
import { LitComponent } from './lit.component';
import { LitDetailComponent } from './lit-detail.component';
import { LitUpdateComponent } from './lit-update.component';

@Injectable({ providedIn: 'root' })
export class LitResolve implements Resolve<ILit> {
  constructor(private service: LitService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILit> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((lit: HttpResponse<Lit>) => {
          if (lit.body) {
            return of(lit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Lit());
  }
}

export const litRoute: Routes = [
  {
    path: '',
    component: LitComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.lit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LitDetailComponent,
    resolve: {
      lit: LitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.lit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LitUpdateComponent,
    resolve: {
      lit: LitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.lit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LitUpdateComponent,
    resolve: {
      lit: LitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.lit.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
