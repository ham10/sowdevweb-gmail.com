import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProdFournis, ProdFournis } from 'app/shared/model/prod-fournis.model';
import { ProdFournisService } from './prod-fournis.service';
import { ProdFournisComponent } from './prod-fournis.component';
import { ProdFournisDetailComponent } from './prod-fournis-detail.component';
import { ProdFournisUpdateComponent } from './prod-fournis-update.component';

@Injectable({ providedIn: 'root' })
export class ProdFournisResolve implements Resolve<IProdFournis> {
  constructor(private service: ProdFournisService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProdFournis> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((prodFournis: HttpResponse<ProdFournis>) => {
          if (prodFournis.body) {
            return of(prodFournis.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProdFournis());
  }
}

export const prodFournisRoute: Routes = [
  {
    path: '',
    component: ProdFournisComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.prodFournis.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProdFournisDetailComponent,
    resolve: {
      prodFournis: ProdFournisResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.prodFournis.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProdFournisUpdateComponent,
    resolve: {
      prodFournis: ProdFournisResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.prodFournis.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProdFournisUpdateComponent,
    resolve: {
      prodFournis: ProdFournisResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.prodFournis.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
