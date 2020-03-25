import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBoxe, Boxe } from 'app/shared/model/boxe.model';
import { BoxeService } from './boxe.service';
import { BoxeComponent } from './boxe.component';
import { BoxeDetailComponent } from './boxe-detail.component';
import { BoxeUpdateComponent } from './boxe-update.component';

@Injectable({ providedIn: 'root' })
export class BoxeResolve implements Resolve<IBoxe> {
  constructor(private service: BoxeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBoxe> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((boxe: HttpResponse<Boxe>) => {
          if (boxe.body) {
            return of(boxe.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Boxe());
  }
}

export const boxeRoute: Routes = [
  {
    path: '',
    component: BoxeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.boxe.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BoxeDetailComponent,
    resolve: {
      boxe: BoxeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.boxe.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BoxeUpdateComponent,
    resolve: {
      boxe: BoxeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.boxe.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BoxeUpdateComponent,
    resolve: {
      boxe: BoxeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.boxe.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
