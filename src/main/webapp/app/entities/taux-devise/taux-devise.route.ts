import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITauxDevise, TauxDevise } from 'app/shared/model/taux-devise.model';
import { TauxDeviseService } from './taux-devise.service';
import { TauxDeviseComponent } from './taux-devise.component';
import { TauxDeviseDetailComponent } from './taux-devise-detail.component';
import { TauxDeviseUpdateComponent } from './taux-devise-update.component';

@Injectable({ providedIn: 'root' })
export class TauxDeviseResolve implements Resolve<ITauxDevise> {
  constructor(private service: TauxDeviseService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITauxDevise> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tauxDevise: HttpResponse<TauxDevise>) => {
          if (tauxDevise.body) {
            return of(tauxDevise.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TauxDevise());
  }
}

export const tauxDeviseRoute: Routes = [
  {
    path: '',
    component: TauxDeviseComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.tauxDevise.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TauxDeviseDetailComponent,
    resolve: {
      tauxDevise: TauxDeviseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.tauxDevise.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TauxDeviseUpdateComponent,
    resolve: {
      tauxDevise: TauxDeviseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.tauxDevise.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TauxDeviseUpdateComponent,
    resolve: {
      tauxDevise: TauxDeviseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.tauxDevise.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
