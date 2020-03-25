import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IChambre, Chambre } from 'app/shared/model/chambre.model';
import { ChambreService } from './chambre.service';
import { ChambreComponent } from './chambre.component';
import { ChambreDetailComponent } from './chambre-detail.component';
import { ChambreUpdateComponent } from './chambre-update.component';

@Injectable({ providedIn: 'root' })
export class ChambreResolve implements Resolve<IChambre> {
  constructor(private service: ChambreService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IChambre> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((chambre: HttpResponse<Chambre>) => {
          if (chambre.body) {
            return of(chambre.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Chambre());
  }
}

export const chambreRoute: Routes = [
  {
    path: '',
    component: ChambreComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.chambre.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ChambreDetailComponent,
    resolve: {
      chambre: ChambreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.chambre.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ChambreUpdateComponent,
    resolve: {
      chambre: ChambreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.chambre.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ChambreUpdateComponent,
    resolve: {
      chambre: ChambreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.chambre.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
