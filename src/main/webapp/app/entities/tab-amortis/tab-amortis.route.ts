import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITabAmortis, TabAmortis } from 'app/shared/model/tab-amortis.model';
import { TabAmortisService } from './tab-amortis.service';
import { TabAmortisComponent } from './tab-amortis.component';
import { TabAmortisDetailComponent } from './tab-amortis-detail.component';
import { TabAmortisUpdateComponent } from './tab-amortis-update.component';

@Injectable({ providedIn: 'root' })
export class TabAmortisResolve implements Resolve<ITabAmortis> {
  constructor(private service: TabAmortisService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITabAmortis> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tabAmortis: HttpResponse<TabAmortis>) => {
          if (tabAmortis.body) {
            return of(tabAmortis.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TabAmortis());
  }
}

export const tabAmortisRoute: Routes = [
  {
    path: '',
    component: TabAmortisComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.tabAmortis.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TabAmortisDetailComponent,
    resolve: {
      tabAmortis: TabAmortisResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.tabAmortis.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TabAmortisUpdateComponent,
    resolve: {
      tabAmortis: TabAmortisResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.tabAmortis.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TabAmortisUpdateComponent,
    resolve: {
      tabAmortis: TabAmortisResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.tabAmortis.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
