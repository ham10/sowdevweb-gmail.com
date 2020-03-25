import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISousFamille, SousFamille } from 'app/shared/model/sous-famille.model';
import { SousFamilleService } from './sous-famille.service';
import { SousFamilleComponent } from './sous-famille.component';
import { SousFamilleDetailComponent } from './sous-famille-detail.component';
import { SousFamilleUpdateComponent } from './sous-famille-update.component';

@Injectable({ providedIn: 'root' })
export class SousFamilleResolve implements Resolve<ISousFamille> {
  constructor(private service: SousFamilleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISousFamille> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sousFamille: HttpResponse<SousFamille>) => {
          if (sousFamille.body) {
            return of(sousFamille.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SousFamille());
  }
}

export const sousFamilleRoute: Routes = [
  {
    path: '',
    component: SousFamilleComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.sousFamille.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SousFamilleDetailComponent,
    resolve: {
      sousFamille: SousFamilleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.sousFamille.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SousFamilleUpdateComponent,
    resolve: {
      sousFamille: SousFamilleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.sousFamille.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SousFamilleUpdateComponent,
    resolve: {
      sousFamille: SousFamilleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.sousFamille.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
