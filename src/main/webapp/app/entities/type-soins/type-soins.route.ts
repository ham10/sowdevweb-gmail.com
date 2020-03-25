import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeSoins, TypeSoins } from 'app/shared/model/type-soins.model';
import { TypeSoinsService } from './type-soins.service';
import { TypeSoinsComponent } from './type-soins.component';
import { TypeSoinsDetailComponent } from './type-soins-detail.component';
import { TypeSoinsUpdateComponent } from './type-soins-update.component';

@Injectable({ providedIn: 'root' })
export class TypeSoinsResolve implements Resolve<ITypeSoins> {
  constructor(private service: TypeSoinsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeSoins> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeSoins: HttpResponse<TypeSoins>) => {
          if (typeSoins.body) {
            return of(typeSoins.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeSoins());
  }
}

export const typeSoinsRoute: Routes = [
  {
    path: '',
    component: TypeSoinsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeSoins.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeSoinsDetailComponent,
    resolve: {
      typeSoins: TypeSoinsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeSoins.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeSoinsUpdateComponent,
    resolve: {
      typeSoins: TypeSoinsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeSoins.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeSoinsUpdateComponent,
    resolve: {
      typeSoins: TypeSoinsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeSoins.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
