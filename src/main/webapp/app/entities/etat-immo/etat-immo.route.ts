import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEtatImmo, EtatImmo } from 'app/shared/model/etat-immo.model';
import { EtatImmoService } from './etat-immo.service';
import { EtatImmoComponent } from './etat-immo.component';
import { EtatImmoDetailComponent } from './etat-immo-detail.component';
import { EtatImmoUpdateComponent } from './etat-immo-update.component';

@Injectable({ providedIn: 'root' })
export class EtatImmoResolve implements Resolve<IEtatImmo> {
  constructor(private service: EtatImmoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEtatImmo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((etatImmo: HttpResponse<EtatImmo>) => {
          if (etatImmo.body) {
            return of(etatImmo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EtatImmo());
  }
}

export const etatImmoRoute: Routes = [
  {
    path: '',
    component: EtatImmoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatImmo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EtatImmoDetailComponent,
    resolve: {
      etatImmo: EtatImmoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatImmo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EtatImmoUpdateComponent,
    resolve: {
      etatImmo: EtatImmoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatImmo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EtatImmoUpdateComponent,
    resolve: {
      etatImmo: EtatImmoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatImmo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
