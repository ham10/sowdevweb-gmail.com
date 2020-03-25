import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICible, Cible } from 'app/shared/model/cible.model';
import { CibleService } from './cible.service';
import { CibleComponent } from './cible.component';
import { CibleDetailComponent } from './cible-detail.component';
import { CibleUpdateComponent } from './cible-update.component';

@Injectable({ providedIn: 'root' })
export class CibleResolve implements Resolve<ICible> {
  constructor(private service: CibleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICible> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cible: HttpResponse<Cible>) => {
          if (cible.body) {
            return of(cible.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Cible());
  }
}

export const cibleRoute: Routes = [
  {
    path: '',
    component: CibleComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.cible.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CibleDetailComponent,
    resolve: {
      cible: CibleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.cible.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CibleUpdateComponent,
    resolve: {
      cible: CibleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.cible.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CibleUpdateComponent,
    resolve: {
      cible: CibleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.cible.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
