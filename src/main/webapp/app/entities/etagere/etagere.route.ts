import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEtagere, Etagere } from 'app/shared/model/etagere.model';
import { EtagereService } from './etagere.service';
import { EtagereComponent } from './etagere.component';
import { EtagereDetailComponent } from './etagere-detail.component';
import { EtagereUpdateComponent } from './etagere-update.component';

@Injectable({ providedIn: 'root' })
export class EtagereResolve implements Resolve<IEtagere> {
  constructor(private service: EtagereService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEtagere> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((etagere: HttpResponse<Etagere>) => {
          if (etagere.body) {
            return of(etagere.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Etagere());
  }
}

export const etagereRoute: Routes = [
  {
    path: '',
    component: EtagereComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etagere.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EtagereDetailComponent,
    resolve: {
      etagere: EtagereResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etagere.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EtagereUpdateComponent,
    resolve: {
      etagere: EtagereResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etagere.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EtagereUpdateComponent,
    resolve: {
      etagere: EtagereResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etagere.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
