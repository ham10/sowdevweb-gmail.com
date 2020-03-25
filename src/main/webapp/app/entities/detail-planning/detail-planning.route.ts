import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDetailPlanning, DetailPlanning } from 'app/shared/model/detail-planning.model';
import { DetailPlanningService } from './detail-planning.service';
import { DetailPlanningComponent } from './detail-planning.component';
import { DetailPlanningDetailComponent } from './detail-planning-detail.component';
import { DetailPlanningUpdateComponent } from './detail-planning-update.component';

@Injectable({ providedIn: 'root' })
export class DetailPlanningResolve implements Resolve<IDetailPlanning> {
  constructor(private service: DetailPlanningService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDetailPlanning> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((detailPlanning: HttpResponse<DetailPlanning>) => {
          if (detailPlanning.body) {
            return of(detailPlanning.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DetailPlanning());
  }
}

export const detailPlanningRoute: Routes = [
  {
    path: '',
    component: DetailPlanningComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.detailPlanning.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DetailPlanningDetailComponent,
    resolve: {
      detailPlanning: DetailPlanningResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.detailPlanning.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DetailPlanningUpdateComponent,
    resolve: {
      detailPlanning: DetailPlanningResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.detailPlanning.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DetailPlanningUpdateComponent,
    resolve: {
      detailPlanning: DetailPlanningResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.detailPlanning.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
