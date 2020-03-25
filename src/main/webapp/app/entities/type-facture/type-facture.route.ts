import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeFacture, TypeFacture } from 'app/shared/model/type-facture.model';
import { TypeFactureService } from './type-facture.service';
import { TypeFactureComponent } from './type-facture.component';
import { TypeFactureDetailComponent } from './type-facture-detail.component';
import { TypeFactureUpdateComponent } from './type-facture-update.component';

@Injectable({ providedIn: 'root' })
export class TypeFactureResolve implements Resolve<ITypeFacture> {
  constructor(private service: TypeFactureService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeFacture> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeFacture: HttpResponse<TypeFacture>) => {
          if (typeFacture.body) {
            return of(typeFacture.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeFacture());
  }
}

export const typeFactureRoute: Routes = [
  {
    path: '',
    component: TypeFactureComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeFacture.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeFactureDetailComponent,
    resolve: {
      typeFacture: TypeFactureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeFacture.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeFactureUpdateComponent,
    resolve: {
      typeFacture: TypeFactureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeFacture.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeFactureUpdateComponent,
    resolve: {
      typeFacture: TypeFactureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeFacture.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
