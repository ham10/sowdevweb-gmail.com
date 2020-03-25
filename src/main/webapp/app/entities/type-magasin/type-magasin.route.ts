import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeMagasin, TypeMagasin } from 'app/shared/model/type-magasin.model';
import { TypeMagasinService } from './type-magasin.service';
import { TypeMagasinComponent } from './type-magasin.component';
import { TypeMagasinDetailComponent } from './type-magasin-detail.component';
import { TypeMagasinUpdateComponent } from './type-magasin-update.component';

@Injectable({ providedIn: 'root' })
export class TypeMagasinResolve implements Resolve<ITypeMagasin> {
  constructor(private service: TypeMagasinService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeMagasin> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeMagasin: HttpResponse<TypeMagasin>) => {
          if (typeMagasin.body) {
            return of(typeMagasin.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeMagasin());
  }
}

export const typeMagasinRoute: Routes = [
  {
    path: '',
    component: TypeMagasinComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeMagasin.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeMagasinDetailComponent,
    resolve: {
      typeMagasin: TypeMagasinResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeMagasin.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeMagasinUpdateComponent,
    resolve: {
      typeMagasin: TypeMagasinResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeMagasin.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeMagasinUpdateComponent,
    resolve: {
      typeMagasin: TypeMagasinResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeMagasin.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
