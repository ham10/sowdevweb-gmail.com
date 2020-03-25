import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICatReport, CatReport } from 'app/shared/model/cat-report.model';
import { CatReportService } from './cat-report.service';
import { CatReportComponent } from './cat-report.component';
import { CatReportDetailComponent } from './cat-report-detail.component';
import { CatReportUpdateComponent } from './cat-report-update.component';

@Injectable({ providedIn: 'root' })
export class CatReportResolve implements Resolve<ICatReport> {
  constructor(private service: CatReportService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICatReport> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((catReport: HttpResponse<CatReport>) => {
          if (catReport.body) {
            return of(catReport.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CatReport());
  }
}

export const catReportRoute: Routes = [
  {
    path: '',
    component: CatReportComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.catReport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CatReportDetailComponent,
    resolve: {
      catReport: CatReportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.catReport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CatReportUpdateComponent,
    resolve: {
      catReport: CatReportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.catReport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CatReportUpdateComponent,
    resolve: {
      catReport: CatReportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.catReport.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
