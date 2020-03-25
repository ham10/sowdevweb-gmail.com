import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IChapCompta, ChapCompta } from 'app/shared/model/chap-compta.model';
import { ChapComptaService } from './chap-compta.service';
import { ChapComptaComponent } from './chap-compta.component';
import { ChapComptaDetailComponent } from './chap-compta-detail.component';
import { ChapComptaUpdateComponent } from './chap-compta-update.component';

@Injectable({ providedIn: 'root' })
export class ChapComptaResolve implements Resolve<IChapCompta> {
  constructor(private service: ChapComptaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IChapCompta> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((chapCompta: HttpResponse<ChapCompta>) => {
          if (chapCompta.body) {
            return of(chapCompta.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ChapCompta());
  }
}

export const chapComptaRoute: Routes = [
  {
    path: '',
    component: ChapComptaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.chapCompta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ChapComptaDetailComponent,
    resolve: {
      chapCompta: ChapComptaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.chapCompta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ChapComptaUpdateComponent,
    resolve: {
      chapCompta: ChapComptaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.chapCompta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ChapComptaUpdateComponent,
    resolve: {
      chapCompta: ChapComptaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.chapCompta.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
