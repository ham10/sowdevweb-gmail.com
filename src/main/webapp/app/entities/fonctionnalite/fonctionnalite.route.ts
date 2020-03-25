import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFonctionnalite, Fonctionnalite } from 'app/shared/model/fonctionnalite.model';
import { FonctionnaliteService } from './fonctionnalite.service';
import { FonctionnaliteComponent } from './fonctionnalite.component';
import { FonctionnaliteDetailComponent } from './fonctionnalite-detail.component';
import { FonctionnaliteUpdateComponent } from './fonctionnalite-update.component';

@Injectable({ providedIn: 'root' })
export class FonctionnaliteResolve implements Resolve<IFonctionnalite> {
  constructor(private service: FonctionnaliteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFonctionnalite> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((fonctionnalite: HttpResponse<Fonctionnalite>) => {
          if (fonctionnalite.body) {
            return of(fonctionnalite.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Fonctionnalite());
  }
}

export const fonctionnaliteRoute: Routes = [
  {
    path: '',
    component: FonctionnaliteComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.fonctionnalite.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FonctionnaliteDetailComponent,
    resolve: {
      fonctionnalite: FonctionnaliteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.fonctionnalite.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FonctionnaliteUpdateComponent,
    resolve: {
      fonctionnalite: FonctionnaliteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.fonctionnalite.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FonctionnaliteUpdateComponent,
    resolve: {
      fonctionnalite: FonctionnaliteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.fonctionnalite.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
