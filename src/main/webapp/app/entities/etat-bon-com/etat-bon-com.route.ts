import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEtatBonCom, EtatBonCom } from 'app/shared/model/etat-bon-com.model';
import { EtatBonComService } from './etat-bon-com.service';
import { EtatBonComComponent } from './etat-bon-com.component';
import { EtatBonComDetailComponent } from './etat-bon-com-detail.component';
import { EtatBonComUpdateComponent } from './etat-bon-com-update.component';

@Injectable({ providedIn: 'root' })
export class EtatBonComResolve implements Resolve<IEtatBonCom> {
  constructor(private service: EtatBonComService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEtatBonCom> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((etatBonCom: HttpResponse<EtatBonCom>) => {
          if (etatBonCom.body) {
            return of(etatBonCom.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EtatBonCom());
  }
}

export const etatBonComRoute: Routes = [
  {
    path: '',
    component: EtatBonComComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatBonCom.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EtatBonComDetailComponent,
    resolve: {
      etatBonCom: EtatBonComResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatBonCom.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EtatBonComUpdateComponent,
    resolve: {
      etatBonCom: EtatBonComResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatBonCom.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EtatBonComUpdateComponent,
    resolve: {
      etatBonCom: EtatBonComResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatBonCom.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
