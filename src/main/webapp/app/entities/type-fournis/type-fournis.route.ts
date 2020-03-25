import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeFournis, TypeFournis } from 'app/shared/model/type-fournis.model';
import { TypeFournisService } from './type-fournis.service';
import { TypeFournisComponent } from './type-fournis.component';
import { TypeFournisDetailComponent } from './type-fournis-detail.component';
import { TypeFournisUpdateComponent } from './type-fournis-update.component';

@Injectable({ providedIn: 'root' })
export class TypeFournisResolve implements Resolve<ITypeFournis> {
  constructor(private service: TypeFournisService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeFournis> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeFournis: HttpResponse<TypeFournis>) => {
          if (typeFournis.body) {
            return of(typeFournis.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeFournis());
  }
}

export const typeFournisRoute: Routes = [
  {
    path: '',
    component: TypeFournisComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeFournis.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeFournisDetailComponent,
    resolve: {
      typeFournis: TypeFournisResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeFournis.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeFournisUpdateComponent,
    resolve: {
      typeFournis: TypeFournisResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeFournis.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeFournisUpdateComponent,
    resolve: {
      typeFournis: TypeFournisResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeFournis.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
