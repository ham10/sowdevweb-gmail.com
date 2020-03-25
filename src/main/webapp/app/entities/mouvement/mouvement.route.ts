import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMouvement, Mouvement } from 'app/shared/model/mouvement.model';
import { MouvementService } from './mouvement.service';
import { MouvementComponent } from './mouvement.component';
import { MouvementDetailComponent } from './mouvement-detail.component';
import { MouvementUpdateComponent } from './mouvement-update.component';

@Injectable({ providedIn: 'root' })
export class MouvementResolve implements Resolve<IMouvement> {
  constructor(private service: MouvementService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMouvement> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((mouvement: HttpResponse<Mouvement>) => {
          if (mouvement.body) {
            return of(mouvement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Mouvement());
  }
}

export const mouvementRoute: Routes = [
  {
    path: '',
    component: MouvementComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.mouvement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MouvementDetailComponent,
    resolve: {
      mouvement: MouvementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.mouvement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MouvementUpdateComponent,
    resolve: {
      mouvement: MouvementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.mouvement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MouvementUpdateComponent,
    resolve: {
      mouvement: MouvementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.mouvement.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
