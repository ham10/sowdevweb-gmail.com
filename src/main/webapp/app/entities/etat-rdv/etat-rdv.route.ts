import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEtatRdv, EtatRdv } from 'app/shared/model/etat-rdv.model';
import { EtatRdvService } from './etat-rdv.service';
import { EtatRdvComponent } from './etat-rdv.component';
import { EtatRdvDetailComponent } from './etat-rdv-detail.component';
import { EtatRdvUpdateComponent } from './etat-rdv-update.component';

@Injectable({ providedIn: 'root' })
export class EtatRdvResolve implements Resolve<IEtatRdv> {
  constructor(private service: EtatRdvService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEtatRdv> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((etatRdv: HttpResponse<EtatRdv>) => {
          if (etatRdv.body) {
            return of(etatRdv.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EtatRdv());
  }
}

export const etatRdvRoute: Routes = [
  {
    path: '',
    component: EtatRdvComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatRdv.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EtatRdvDetailComponent,
    resolve: {
      etatRdv: EtatRdvResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatRdv.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EtatRdvUpdateComponent,
    resolve: {
      etatRdv: EtatRdvResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatRdv.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EtatRdvUpdateComponent,
    resolve: {
      etatRdv: EtatRdvResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatRdv.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
