import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILigneLivraison, LigneLivraison } from 'app/shared/model/ligne-livraison.model';
import { LigneLivraisonService } from './ligne-livraison.service';
import { LigneLivraisonComponent } from './ligne-livraison.component';
import { LigneLivraisonDetailComponent } from './ligne-livraison-detail.component';
import { LigneLivraisonUpdateComponent } from './ligne-livraison-update.component';

@Injectable({ providedIn: 'root' })
export class LigneLivraisonResolve implements Resolve<ILigneLivraison> {
  constructor(private service: LigneLivraisonService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILigneLivraison> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ligneLivraison: HttpResponse<LigneLivraison>) => {
          if (ligneLivraison.body) {
            return of(ligneLivraison.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LigneLivraison());
  }
}

export const ligneLivraisonRoute: Routes = [
  {
    path: '',
    component: LigneLivraisonComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.ligneLivraison.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LigneLivraisonDetailComponent,
    resolve: {
      ligneLivraison: LigneLivraisonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.ligneLivraison.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LigneLivraisonUpdateComponent,
    resolve: {
      ligneLivraison: LigneLivraisonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.ligneLivraison.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LigneLivraisonUpdateComponent,
    resolve: {
      ligneLivraison: LigneLivraisonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.ligneLivraison.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
