import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUnite, Unite } from 'app/shared/model/unite.model';
import { UniteService } from './unite.service';
import { UniteComponent } from './unite.component';
import { UniteDetailComponent } from './unite-detail.component';
import { UniteUpdateComponent } from './unite-update.component';

@Injectable({ providedIn: 'root' })
export class UniteResolve implements Resolve<IUnite> {
  constructor(private service: UniteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUnite> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((unite: HttpResponse<Unite>) => {
          if (unite.body) {
            return of(unite.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Unite());
  }
}

export const uniteRoute: Routes = [
  {
    path: '',
    component: UniteComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.unite.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UniteDetailComponent,
    resolve: {
      unite: UniteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.unite.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UniteUpdateComponent,
    resolve: {
      unite: UniteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.unite.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UniteUpdateComponent,
    resolve: {
      unite: UniteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.unite.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
