import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEtatFacture, EtatFacture } from 'app/shared/model/etat-facture.model';
import { EtatFactureService } from './etat-facture.service';
import { EtatFactureComponent } from './etat-facture.component';
import { EtatFactureDetailComponent } from './etat-facture-detail.component';
import { EtatFactureUpdateComponent } from './etat-facture-update.component';

@Injectable({ providedIn: 'root' })
export class EtatFactureResolve implements Resolve<IEtatFacture> {
  constructor(private service: EtatFactureService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEtatFacture> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((etatFacture: HttpResponse<EtatFacture>) => {
          if (etatFacture.body) {
            return of(etatFacture.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EtatFacture());
  }
}

export const etatFactureRoute: Routes = [
  {
    path: '',
    component: EtatFactureComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatFacture.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EtatFactureDetailComponent,
    resolve: {
      etatFacture: EtatFactureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatFacture.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EtatFactureUpdateComponent,
    resolve: {
      etatFacture: EtatFactureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatFacture.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EtatFactureUpdateComponent,
    resolve: {
      etatFacture: EtatFactureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatFacture.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
