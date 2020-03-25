import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeBonCom, TypeBonCom } from 'app/shared/model/type-bon-com.model';
import { TypeBonComService } from './type-bon-com.service';
import { TypeBonComComponent } from './type-bon-com.component';
import { TypeBonComDetailComponent } from './type-bon-com-detail.component';
import { TypeBonComUpdateComponent } from './type-bon-com-update.component';

@Injectable({ providedIn: 'root' })
export class TypeBonComResolve implements Resolve<ITypeBonCom> {
  constructor(private service: TypeBonComService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeBonCom> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeBonCom: HttpResponse<TypeBonCom>) => {
          if (typeBonCom.body) {
            return of(typeBonCom.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeBonCom());
  }
}

export const typeBonComRoute: Routes = [
  {
    path: '',
    component: TypeBonComComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeBonCom.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeBonComDetailComponent,
    resolve: {
      typeBonCom: TypeBonComResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeBonCom.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeBonComUpdateComponent,
    resolve: {
      typeBonCom: TypeBonComResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeBonCom.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeBonComUpdateComponent,
    resolve: {
      typeBonCom: TypeBonComResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeBonCom.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
