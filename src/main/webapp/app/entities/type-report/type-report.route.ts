import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeReport, TypeReport } from 'app/shared/model/type-report.model';
import { TypeReportService } from './type-report.service';
import { TypeReportComponent } from './type-report.component';
import { TypeReportDetailComponent } from './type-report-detail.component';
import { TypeReportUpdateComponent } from './type-report-update.component';

@Injectable({ providedIn: 'root' })
export class TypeReportResolve implements Resolve<ITypeReport> {
  constructor(private service: TypeReportService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeReport> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeReport: HttpResponse<TypeReport>) => {
          if (typeReport.body) {
            return of(typeReport.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeReport());
  }
}

export const typeReportRoute: Routes = [
  {
    path: '',
    component: TypeReportComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeReport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeReportDetailComponent,
    resolve: {
      typeReport: TypeReportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeReport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeReportUpdateComponent,
    resolve: {
      typeReport: TypeReportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeReport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeReportUpdateComponent,
    resolve: {
      typeReport: TypeReportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typeReport.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
