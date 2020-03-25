import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRDV, RDV } from 'app/shared/model/rdv.model';
import { RDVService } from './rdv.service';
import { RDVComponent } from './rdv.component';
import { RDVDetailComponent } from './rdv-detail.component';
import { RDVUpdateComponent } from './rdv-update.component';
import { RdvHomeComponent } from 'app/entities/rdv/rdv-home/rdv-home.component';

@Injectable({ providedIn: 'root' })
export class RDVResolve implements Resolve<IRDV> {
  constructor(private service: RDVService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRDV> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((rDV: HttpResponse<RDV>) => {
          if (rDV.body) {
            return of(rDV.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RDV());
  }
}

export const rDVRoute: Routes = [
  {
    path: '',
    component: RdvHomeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.rDV.home.title'
    },
    canActivate: [UserRouteAccessService],
    children: [
      { path: '', component: RDVComponent, canActivate: [UserRouteAccessService] },
      {
        path: ':id/view',
        component: RDVDetailComponent,
        resolve: {
          rDV: RDVResolve
        },
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'hpdApp.rDV.home.title'
        },
        canActivate: [UserRouteAccessService]
      },
      {
        path: 'new',
        component: RDVUpdateComponent,
        resolve: {
          rDV: RDVResolve
        },
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'hpdApp.rDV.home.title'
        },
        canActivate: [UserRouteAccessService]
      },
      {
        path: ':id/edit',
        component: RDVUpdateComponent,
        resolve: {
          rDV: RDVResolve
        },
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'hpdApp.rDV.home.title'
        },
        canActivate: [UserRouteAccessService]
      }
    ]
  }
];
