import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IServices, Services } from 'app/shared/model/services.model';
import { ServicesService } from './services.service';
import { ServicesComponent } from './services.component';
import { ServicesDetailComponent } from './services-detail.component';
import { ServicesUpdateComponent } from './services-update.component';

@Injectable({ providedIn: 'root' })
export class ServicesResolve implements Resolve<IServices> {
  constructor(private service: ServicesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IServices> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((services: HttpResponse<Services>) => {
          if (services.body) {
            return of(services.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Services());
  }
}

export const servicesRoute: Routes = [
  {
    path: '',
    component: ServicesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.services.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServicesDetailComponent,
    resolve: {
      services: ServicesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.services.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServicesUpdateComponent,
    resolve: {
      services: ServicesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.services.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServicesUpdateComponent,
    resolve: {
      services: ServicesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.services.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
