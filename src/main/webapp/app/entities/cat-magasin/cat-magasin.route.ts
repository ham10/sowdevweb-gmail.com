import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICatMagasin, CatMagasin } from 'app/shared/model/cat-magasin.model';
import { CatMagasinService } from './cat-magasin.service';
import { CatMagasinComponent } from './cat-magasin.component';
import { CatMagasinDetailComponent } from './cat-magasin-detail.component';
import { CatMagasinUpdateComponent } from './cat-magasin-update.component';

@Injectable({ providedIn: 'root' })
export class CatMagasinResolve implements Resolve<ICatMagasin> {
  constructor(private service: CatMagasinService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICatMagasin> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((catMagasin: HttpResponse<CatMagasin>) => {
          if (catMagasin.body) {
            return of(catMagasin.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CatMagasin());
  }
}

export const catMagasinRoute: Routes = [
  {
    path: '',
    component: CatMagasinComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.catMagasin.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CatMagasinDetailComponent,
    resolve: {
      catMagasin: CatMagasinResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.catMagasin.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CatMagasinUpdateComponent,
    resolve: {
      catMagasin: CatMagasinResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.catMagasin.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CatMagasinUpdateComponent,
    resolve: {
      catMagasin: CatMagasinResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.catMagasin.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
