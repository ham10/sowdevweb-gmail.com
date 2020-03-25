import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEtatCaisse, EtatCaisse } from 'app/shared/model/etat-caisse.model';
import { EtatCaisseService } from './etat-caisse.service';
import { EtatCaisseComponent } from './etat-caisse.component';
import { EtatCaisseDetailComponent } from './etat-caisse-detail.component';
import { EtatCaisseUpdateComponent } from './etat-caisse-update.component';

@Injectable({ providedIn: 'root' })
export class EtatCaisseResolve implements Resolve<IEtatCaisse> {
  constructor(private service: EtatCaisseService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEtatCaisse> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((etatCaisse: HttpResponse<EtatCaisse>) => {
          if (etatCaisse.body) {
            return of(etatCaisse.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EtatCaisse());
  }
}

export const etatCaisseRoute: Routes = [
  {
    path: '',
    component: EtatCaisseComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatCaisse.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EtatCaisseDetailComponent,
    resolve: {
      etatCaisse: EtatCaisseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatCaisse.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EtatCaisseUpdateComponent,
    resolve: {
      etatCaisse: EtatCaisseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatCaisse.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EtatCaisseUpdateComponent,
    resolve: {
      etatCaisse: EtatCaisseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatCaisse.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
