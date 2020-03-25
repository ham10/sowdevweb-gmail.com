import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEtatOperation, EtatOperation } from 'app/shared/model/etat-operation.model';
import { EtatOperationService } from './etat-operation.service';
import { EtatOperationComponent } from './etat-operation.component';
import { EtatOperationDetailComponent } from './etat-operation-detail.component';
import { EtatOperationUpdateComponent } from './etat-operation-update.component';

@Injectable({ providedIn: 'root' })
export class EtatOperationResolve implements Resolve<IEtatOperation> {
  constructor(private service: EtatOperationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEtatOperation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((etatOperation: HttpResponse<EtatOperation>) => {
          if (etatOperation.body) {
            return of(etatOperation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EtatOperation());
  }
}

export const etatOperationRoute: Routes = [
  {
    path: '',
    component: EtatOperationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatOperation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EtatOperationDetailComponent,
    resolve: {
      etatOperation: EtatOperationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatOperation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EtatOperationUpdateComponent,
    resolve: {
      etatOperation: EtatOperationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatOperation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EtatOperationUpdateComponent,
    resolve: {
      etatOperation: EtatOperationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.etatOperation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
