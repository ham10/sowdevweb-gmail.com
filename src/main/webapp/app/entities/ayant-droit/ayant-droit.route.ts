import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAyantDroit, AyantDroit } from 'app/shared/model/ayant-droit.model';
import { AyantDroitService } from './ayant-droit.service';
import { AyantDroitComponent } from './ayant-droit.component';
import { AyantDroitDetailComponent } from './ayant-droit-detail.component';
import { AyantDroitUpdateComponent } from './ayant-droit-update.component';

@Injectable({ providedIn: 'root' })
export class AyantDroitResolve implements Resolve<IAyantDroit> {
  constructor(private service: AyantDroitService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAyantDroit> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ayantDroit: HttpResponse<AyantDroit>) => {
          if (ayantDroit.body) {
            return of(ayantDroit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AyantDroit());
  }
}

export const ayantDroitRoute: Routes = [
  {
    path: '',
    component: AyantDroitComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.ayantDroit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AyantDroitDetailComponent,
    resolve: {
      ayantDroit: AyantDroitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.ayantDroit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AyantDroitUpdateComponent,
    resolve: {
      ayantDroit: AyantDroitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.ayantDroit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AyantDroitUpdateComponent,
    resolve: {
      ayantDroit: AyantDroitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.ayantDroit.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
