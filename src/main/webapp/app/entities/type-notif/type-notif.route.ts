import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeNotif, TypeNotif } from 'app/shared/model/type-notif.model';
import { TypeNotifService } from './type-notif.service';
import { TypeNotifComponent } from './type-notif.component';
import { TypeNotifDetailComponent } from './type-notif-detail.component';
import { TypeNotifUpdateComponent } from './type-notif-update.component';

@Injectable({ providedIn: 'root' })
export class TypeNotifResolve implements Resolve<ITypeNotif> {
  constructor(private service: TypeNotifService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeNotif> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeNotif: HttpResponse<TypeNotif>) => {
          if (typeNotif.body) {
            return of(typeNotif.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeNotif());
  }
}

export const typeNotifRoute: Routes = [
  {
    path: '',
    component: TypeNotifComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeNotif.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeNotifDetailComponent,
    resolve: {
      typeNotif: TypeNotifResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeNotif.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeNotifUpdateComponent,
    resolve: {
      typeNotif: TypeNotifResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeNotif.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeNotifUpdateComponent,
    resolve: {
      typeNotif: TypeNotifResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeNotif.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
