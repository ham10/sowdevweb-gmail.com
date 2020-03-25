import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDeptServices, DeptServices } from 'app/shared/model/dept-services.model';
import { DeptServicesService } from './dept-services.service';
import { DeptServicesComponent } from './dept-services.component';
import { DeptServicesDetailComponent } from './dept-services-detail.component';
import { DeptServicesUpdateComponent } from './dept-services-update.component';

@Injectable({ providedIn: 'root' })
export class DeptServicesResolve implements Resolve<IDeptServices> {
  constructor(private service: DeptServicesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDeptServices> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((deptServices: HttpResponse<DeptServices>) => {
          if (deptServices.body) {
            return of(deptServices.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DeptServices());
  }
}

export const deptServicesRoute: Routes = [
  {
    path: '',
    component: DeptServicesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.deptServices.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DeptServicesDetailComponent,
    resolve: {
      deptServices: DeptServicesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.deptServices.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DeptServicesUpdateComponent,
    resolve: {
      deptServices: DeptServicesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.deptServices.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DeptServicesUpdateComponent,
    resolve: {
      deptServices: DeptServicesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.deptServices.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
