import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypePlanning, TypePlanning } from 'app/shared/model/type-planning.model';
import { TypePlanningService } from './type-planning.service';
import { TypePlanningComponent } from './type-planning.component';
import { TypePlanningDetailComponent } from './type-planning-detail.component';
import { TypePlanningUpdateComponent } from './type-planning-update.component';

@Injectable({ providedIn: 'root' })
export class TypePlanningResolve implements Resolve<ITypePlanning> {
  constructor(private service: TypePlanningService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypePlanning> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typePlanning: HttpResponse<TypePlanning>) => {
          if (typePlanning.body) {
            return of(typePlanning.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypePlanning());
  }
}

export const typePlanningRoute: Routes = [
  {
    path: '',
    component: TypePlanningComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePlanning.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypePlanningDetailComponent,
    resolve: {
      typePlanning: TypePlanningResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePlanning.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypePlanningUpdateComponent,
    resolve: {
      typePlanning: TypePlanningResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePlanning.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypePlanningUpdateComponent,
    resolve: {
      typePlanning: TypePlanningResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePlanning.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
