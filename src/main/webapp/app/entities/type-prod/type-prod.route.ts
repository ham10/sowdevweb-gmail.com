import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeProd, TypeProd } from 'app/shared/model/type-prod.model';
import { TypeProdService } from './type-prod.service';
import { TypeProdComponent } from './type-prod.component';
import { TypeProdDetailComponent } from './type-prod-detail.component';
import { TypeProdUpdateComponent } from './type-prod-update.component';

@Injectable({ providedIn: 'root' })
export class TypeProdResolve implements Resolve<ITypeProd> {
  constructor(private service: TypeProdService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeProd> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeProd: HttpResponse<TypeProd>) => {
          if (typeProd.body) {
            return of(typeProd.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeProd());
  }
}

export const typeProdRoute: Routes = [
  {
    path: '',
    component: TypeProdComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeProd.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeProdDetailComponent,
    resolve: {
      typeProd: TypeProdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeProd.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeProdUpdateComponent,
    resolve: {
      typeProd: TypeProdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeProd.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeProdUpdateComponent,
    resolve: {
      typeProd: TypeProdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeProd.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
