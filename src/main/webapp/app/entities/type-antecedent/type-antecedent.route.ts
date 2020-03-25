import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeAntecedent, TypeAntecedent } from 'app/shared/model/type-antecedent.model';
import { TypeAntecedentService } from './type-antecedent.service';
import { TypeAntecedentComponent } from './type-antecedent.component';
import { TypeAntecedentDetailComponent } from './type-antecedent-detail.component';
import { TypeAntecedentUpdateComponent } from './type-antecedent-update.component';

@Injectable({ providedIn: 'root' })
export class TypeAntecedentResolve implements Resolve<ITypeAntecedent> {
  constructor(private service: TypeAntecedentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeAntecedent> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeAntecedent: HttpResponse<TypeAntecedent>) => {
          if (typeAntecedent.body) {
            return of(typeAntecedent.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeAntecedent());
  }
}

export const typeAntecedentRoute: Routes = [
  {
    path: '',
    component: TypeAntecedentComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeAntecedent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeAntecedentDetailComponent,
    resolve: {
      typeAntecedent: TypeAntecedentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeAntecedent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeAntecedentUpdateComponent,
    resolve: {
      typeAntecedent: TypeAntecedentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeAntecedent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeAntecedentUpdateComponent,
    resolve: {
      typeAntecedent: TypeAntecedentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeAntecedent.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
