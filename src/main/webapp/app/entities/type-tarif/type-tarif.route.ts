import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeTarif, TypeTarif } from 'app/shared/model/type-tarif.model';
import { TypeTarifService } from './type-tarif.service';
import { TypeTarifComponent } from './type-tarif.component';
import { TypeTarifDetailComponent } from './type-tarif-detail.component';
import { TypeTarifUpdateComponent } from './type-tarif-update.component';

@Injectable({ providedIn: 'root' })
export class TypeTarifResolve implements Resolve<ITypeTarif> {
  constructor(private service: TypeTarifService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeTarif> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeTarif: HttpResponse<TypeTarif>) => {
          if (typeTarif.body) {
            return of(typeTarif.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeTarif());
  }
}

export const typeTarifRoute: Routes = [
  {
    path: '',
    component: TypeTarifComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeTarif.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeTarifDetailComponent,
    resolve: {
      typeTarif: TypeTarifResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeTarif.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeTarifUpdateComponent,
    resolve: {
      typeTarif: TypeTarifResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeTarif.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeTarifUpdateComponent,
    resolve: {
      typeTarif: TypeTarifResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeTarif.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
