import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBonLivraison, BonLivraison } from 'app/shared/model/bon-livraison.model';
import { BonLivraisonService } from './bon-livraison.service';
import { BonLivraisonComponent } from './bon-livraison.component';
import { BonLivraisonDetailComponent } from './bon-livraison-detail.component';
import { BonLivraisonUpdateComponent } from './bon-livraison-update.component';

@Injectable({ providedIn: 'root' })
export class BonLivraisonResolve implements Resolve<IBonLivraison> {
  constructor(private service: BonLivraisonService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBonLivraison> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bonLivraison: HttpResponse<BonLivraison>) => {
          if (bonLivraison.body) {
            return of(bonLivraison.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BonLivraison());
  }
}

export const bonLivraisonRoute: Routes = [
  {
    path: '',
    component: BonLivraisonComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.bonLivraison.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BonLivraisonDetailComponent,
    resolve: {
      bonLivraison: BonLivraisonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.bonLivraison.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BonLivraisonUpdateComponent,
    resolve: {
      bonLivraison: BonLivraisonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.bonLivraison.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BonLivraisonUpdateComponent,
    resolve: {
      bonLivraison: BonLivraisonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.bonLivraison.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
