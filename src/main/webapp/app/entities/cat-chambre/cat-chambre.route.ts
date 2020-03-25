import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICatChambre, CatChambre } from 'app/shared/model/cat-chambre.model';
import { CatChambreService } from './cat-chambre.service';
import { CatChambreComponent } from './cat-chambre.component';
import { CatChambreDetailComponent } from './cat-chambre-detail.component';
import { CatChambreUpdateComponent } from './cat-chambre-update.component';

@Injectable({ providedIn: 'root' })
export class CatChambreResolve implements Resolve<ICatChambre> {
  constructor(private service: CatChambreService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICatChambre> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((catChambre: HttpResponse<CatChambre>) => {
          if (catChambre.body) {
            return of(catChambre.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CatChambre());
  }
}

export const catChambreRoute: Routes = [
  {
    path: '',
    component: CatChambreComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.catChambre.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CatChambreDetailComponent,
    resolve: {
      catChambre: CatChambreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.catChambre.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CatChambreUpdateComponent,
    resolve: {
      catChambre: CatChambreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.catChambre.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CatChambreUpdateComponent,
    resolve: {
      catChambre: CatChambreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.catChambre.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
