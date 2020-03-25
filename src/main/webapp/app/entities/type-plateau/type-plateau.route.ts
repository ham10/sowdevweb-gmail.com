import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypePlateau, TypePlateau } from 'app/shared/model/type-plateau.model';
import { TypePlateauService } from './type-plateau.service';
import { TypePlateauComponent } from './type-plateau.component';
import { TypePlateauDetailComponent } from './type-plateau-detail.component';
import { TypePlateauUpdateComponent } from './type-plateau-update.component';

@Injectable({ providedIn: 'root' })
export class TypePlateauResolve implements Resolve<ITypePlateau> {
  constructor(private service: TypePlateauService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypePlateau> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typePlateau: HttpResponse<TypePlateau>) => {
          if (typePlateau.body) {
            return of(typePlateau.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypePlateau());
  }
}

export const typePlateauRoute: Routes = [
  {
    path: '',
    component: TypePlateauComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePlateau.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypePlateauDetailComponent,
    resolve: {
      typePlateau: TypePlateauResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePlateau.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypePlateauUpdateComponent,
    resolve: {
      typePlateau: TypePlateauResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePlateau.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypePlateauUpdateComponent,
    resolve: {
      typePlateau: TypePlateauResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePlateau.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
