import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRayon, Rayon } from 'app/shared/model/rayon.model';
import { RayonService } from './rayon.service';
import { RayonComponent } from './rayon.component';
import { RayonDetailComponent } from './rayon-detail.component';
import { RayonUpdateComponent } from './rayon-update.component';

@Injectable({ providedIn: 'root' })
export class RayonResolve implements Resolve<IRayon> {
  constructor(private service: RayonService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRayon> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((rayon: HttpResponse<Rayon>) => {
          if (rayon.body) {
            return of(rayon.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Rayon());
  }
}

export const rayonRoute: Routes = [
  {
    path: '',
    component: RayonComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.rayon.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RayonDetailComponent,
    resolve: {
      rayon: RayonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.rayon.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RayonUpdateComponent,
    resolve: {
      rayon: RayonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.rayon.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RayonUpdateComponent,
    resolve: {
      rayon: RayonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.rayon.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
