import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISitMat, SitMat } from 'app/shared/model/sit-mat.model';
import { SitMatService } from './sit-mat.service';
import { SitMatComponent } from './sit-mat.component';
import { SitMatDetailComponent } from './sit-mat-detail.component';
import { SitMatUpdateComponent } from './sit-mat-update.component';

@Injectable({ providedIn: 'root' })
export class SitMatResolve implements Resolve<ISitMat> {
  constructor(private service: SitMatService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISitMat> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sitMat: HttpResponse<SitMat>) => {
          if (sitMat.body) {
            return of(sitMat.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SitMat());
  }
}

export const sitMatRoute: Routes = [
  {
    path: '',
    component: SitMatComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.sitMat.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SitMatDetailComponent,
    resolve: {
      sitMat: SitMatResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.sitMat.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SitMatUpdateComponent,
    resolve: {
      sitMat: SitMatResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.sitMat.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SitMatUpdateComponent,
    resolve: {
      sitMat: SitMatResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.sitMat.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
