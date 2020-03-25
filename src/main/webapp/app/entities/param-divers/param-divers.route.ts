import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IParamDivers, ParamDivers } from 'app/shared/model/param-divers.model';
import { ParamDiversService } from './param-divers.service';
import { ParamDiversComponent } from './param-divers.component';
import { ParamDiversDetailComponent } from './param-divers-detail.component';
import { ParamDiversUpdateComponent } from './param-divers-update.component';

@Injectable({ providedIn: 'root' })
export class ParamDiversResolve implements Resolve<IParamDivers> {
  constructor(private service: ParamDiversService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IParamDivers> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((paramDivers: HttpResponse<ParamDivers>) => {
          if (paramDivers.body) {
            return of(paramDivers.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ParamDivers());
  }
}

export const paramDiversRoute: Routes = [
  {
    path: '',
    component: ParamDiversComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.paramDivers.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ParamDiversDetailComponent,
    resolve: {
      paramDivers: ParamDiversResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.paramDivers.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ParamDiversUpdateComponent,
    resolve: {
      paramDivers: ParamDiversResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.paramDivers.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ParamDiversUpdateComponent,
    resolve: {
      paramDivers: ParamDiversResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.paramDivers.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
