import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeServices, TypeServices } from 'app/shared/model/type-services.model';
import { TypeServicesService } from './type-services.service';
import { TypeServicesComponent } from './type-services.component';
import { TypeServicesDetailComponent } from './type-services-detail.component';
import { TypeServicesUpdateComponent } from './type-services-update.component';

@Injectable({ providedIn: 'root' })
export class TypeServicesResolve implements Resolve<ITypeServices> {
  constructor(private service: TypeServicesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeServices> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeServices: HttpResponse<TypeServices>) => {
          if (typeServices.body) {
            return of(typeServices.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeServices());
  }
}

export const typeServicesRoute: Routes = [
  {
    path: '',
    component: TypeServicesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeServices.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeServicesDetailComponent,
    resolve: {
      typeServices: TypeServicesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeServices.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeServicesUpdateComponent,
    resolve: {
      typeServices: TypeServicesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeServices.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeServicesUpdateComponent,
    resolve: {
      typeServices: TypeServicesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeServices.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
