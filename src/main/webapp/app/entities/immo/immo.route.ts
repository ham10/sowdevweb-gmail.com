import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IImmo, Immo } from 'app/shared/model/immo.model';
import { ImmoService } from './immo.service';
import { ImmoComponent } from './immo.component';
import { ImmoDetailComponent } from './immo-detail.component';
import { ImmoUpdateComponent } from './immo-update.component';

@Injectable({ providedIn: 'root' })
export class ImmoResolve implements Resolve<IImmo> {
  constructor(private service: ImmoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IImmo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((immo: HttpResponse<Immo>) => {
          if (immo.body) {
            return of(immo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Immo());
  }
}

export const immoRoute: Routes = [
  {
    path: '',
    component: ImmoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.immo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ImmoDetailComponent,
    resolve: {
      immo: ImmoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.immo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ImmoUpdateComponent,
    resolve: {
      immo: ImmoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.immo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ImmoUpdateComponent,
    resolve: {
      immo: ImmoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.immo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
