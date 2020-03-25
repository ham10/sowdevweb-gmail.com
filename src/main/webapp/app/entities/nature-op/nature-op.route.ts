import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INatureOp, NatureOp } from 'app/shared/model/nature-op.model';
import { NatureOpService } from './nature-op.service';
import { NatureOpComponent } from './nature-op.component';
import { NatureOpDetailComponent } from './nature-op-detail.component';
import { NatureOpUpdateComponent } from './nature-op-update.component';

@Injectable({ providedIn: 'root' })
export class NatureOpResolve implements Resolve<INatureOp> {
  constructor(private service: NatureOpService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INatureOp> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((natureOp: HttpResponse<NatureOp>) => {
          if (natureOp.body) {
            return of(natureOp.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NatureOp());
  }
}

export const natureOpRoute: Routes = [
  {
    path: '',
    component: NatureOpComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.natureOp.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NatureOpDetailComponent,
    resolve: {
      natureOp: NatureOpResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.natureOp.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NatureOpUpdateComponent,
    resolve: {
      natureOp: NatureOpResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.natureOp.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NatureOpUpdateComponent,
    resolve: {
      natureOp: NatureOpResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.natureOp.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
