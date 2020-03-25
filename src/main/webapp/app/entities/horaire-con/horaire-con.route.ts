import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IHoraireCon, HoraireCon } from 'app/shared/model/horaire-con.model';
import { HoraireConService } from './horaire-con.service';
import { HoraireConComponent } from './horaire-con.component';
import { HoraireConDetailComponent } from './horaire-con-detail.component';
import { HoraireConUpdateComponent } from './horaire-con-update.component';

@Injectable({ providedIn: 'root' })
export class HoraireConResolve implements Resolve<IHoraireCon> {
  constructor(private service: HoraireConService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHoraireCon> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((horaireCon: HttpResponse<HoraireCon>) => {
          if (horaireCon.body) {
            return of(horaireCon.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HoraireCon());
  }
}

export const horaireConRoute: Routes = [
  {
    path: '',
    component: HoraireConComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.horaireCon.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: HoraireConDetailComponent,
    resolve: {
      horaireCon: HoraireConResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.horaireCon.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: HoraireConUpdateComponent,
    resolve: {
      horaireCon: HoraireConResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.horaireCon.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: HoraireConUpdateComponent,
    resolve: {
      horaireCon: HoraireConResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.horaireCon.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
