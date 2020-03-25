import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBonDeCommande, BonDeCommande } from 'app/shared/model/bon-de-commande.model';
import { BonDeCommandeService } from './bon-de-commande.service';
import { BonDeCommandeComponent } from './bon-de-commande.component';
import { BonDeCommandeDetailComponent } from './bon-de-commande-detail.component';
import { BonDeCommandeUpdateComponent } from './bon-de-commande-update.component';

@Injectable({ providedIn: 'root' })
export class BonDeCommandeResolve implements Resolve<IBonDeCommande> {
  constructor(private service: BonDeCommandeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBonDeCommande> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bonDeCommande: HttpResponse<BonDeCommande>) => {
          if (bonDeCommande.body) {
            return of(bonDeCommande.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BonDeCommande());
  }
}

export const bonDeCommandeRoute: Routes = [
  {
    path: '',
    component: BonDeCommandeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.bonDeCommande.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BonDeCommandeDetailComponent,
    resolve: {
      bonDeCommande: BonDeCommandeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.bonDeCommande.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BonDeCommandeUpdateComponent,
    resolve: {
      bonDeCommande: BonDeCommandeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.bonDeCommande.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BonDeCommandeUpdateComponent,
    resolve: {
      bonDeCommande: BonDeCommandeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.bonDeCommande.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
