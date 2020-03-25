import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IResultatActe, ResultatActe } from 'app/shared/model/resultat-acte.model';
import { ResultatActeService } from './resultat-acte.service';
import { ResultatActeComponent } from './resultat-acte.component';
import { ResultatActeDetailComponent } from './resultat-acte-detail.component';
import { ResultatActeUpdateComponent } from './resultat-acte-update.component';

@Injectable({ providedIn: 'root' })
export class ResultatActeResolve implements Resolve<IResultatActe> {
  constructor(private service: ResultatActeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResultatActe> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((resultatActe: HttpResponse<ResultatActe>) => {
          if (resultatActe.body) {
            return of(resultatActe.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ResultatActe());
  }
}

export const resultatActeRoute: Routes = [
  {
    path: '',
    component: ResultatActeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.resultatActe.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ResultatActeDetailComponent,
    resolve: {
      resultatActe: ResultatActeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.resultatActe.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ResultatActeUpdateComponent,
    resolve: {
      resultatActe: ResultatActeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.resultatActe.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ResultatActeUpdateComponent,
    resolve: {
      resultatActe: ResultatActeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.resultatActe.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
