import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IParamSys, ParamSys } from 'app/shared/model/param-sys.model';
import { ParamSysService } from './param-sys.service';
import { ParamSysComponent } from './param-sys.component';
import { ParamSysDetailComponent } from './param-sys-detail.component';
import { ParamSysUpdateComponent } from './param-sys-update.component';

@Injectable({ providedIn: 'root' })
export class ParamSysResolve implements Resolve<IParamSys> {
  constructor(private service: ParamSysService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IParamSys> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((paramSys: HttpResponse<ParamSys>) => {
          if (paramSys.body) {
            return of(paramSys.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ParamSys());
  }
}

export const paramSysRoute: Routes = [
  {
    path: '',
    component: ParamSysComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.paramSys.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ParamSysDetailComponent,
    resolve: {
      paramSys: ParamSysResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.paramSys.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ParamSysUpdateComponent,
    resolve: {
      paramSys: ParamSysResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.paramSys.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ParamSysUpdateComponent,
    resolve: {
      paramSys: ParamSysResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.paramSys.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
