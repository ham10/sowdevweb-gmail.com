import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlat, Plat } from 'app/shared/model/plat.model';
import { PlatService } from './plat.service';
import { PlatComponent } from './plat.component';
import { PlatDetailComponent } from './plat-detail.component';
import { PlatUpdateComponent } from './plat-update.component';

@Injectable({ providedIn: 'root' })
export class PlatResolve implements Resolve<IPlat> {
  constructor(private service: PlatService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlat> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((plat: HttpResponse<Plat>) => {
          if (plat.body) {
            return of(plat.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Plat());
  }
}

export const platRoute: Routes = [
  {
    path: '',
    component: PlatComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.plat.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PlatDetailComponent,
    resolve: {
      plat: PlatResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.plat.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PlatUpdateComponent,
    resolve: {
      plat: PlatResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.plat.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PlatUpdateComponent,
    resolve: {
      plat: PlatResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.plat.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
