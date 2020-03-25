import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeChamps, TypeChamps } from 'app/shared/model/type-champs.model';
import { TypeChampsService } from './type-champs.service';
import { TypeChampsComponent } from './type-champs.component';
import { TypeChampsDetailComponent } from './type-champs-detail.component';
import { TypeChampsUpdateComponent } from './type-champs-update.component';

@Injectable({ providedIn: 'root' })
export class TypeChampsResolve implements Resolve<ITypeChamps> {
  constructor(private service: TypeChampsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeChamps> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeChamps: HttpResponse<TypeChamps>) => {
          if (typeChamps.body) {
            return of(typeChamps.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeChamps());
  }
}

export const typeChampsRoute: Routes = [
  {
    path: '',
    component: TypeChampsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeChamps.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeChampsDetailComponent,
    resolve: {
      typeChamps: TypeChampsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeChamps.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeChampsUpdateComponent,
    resolve: {
      typeChamps: TypeChampsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeChamps.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeChampsUpdateComponent,
    resolve: {
      typeChamps: TypeChampsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeChamps.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
