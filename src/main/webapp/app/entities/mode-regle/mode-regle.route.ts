import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IModeRegle, ModeRegle } from 'app/shared/model/mode-regle.model';
import { ModeRegleService } from './mode-regle.service';
import { ModeRegleComponent } from './mode-regle.component';
import { ModeRegleDetailComponent } from './mode-regle-detail.component';
import { ModeRegleUpdateComponent } from './mode-regle-update.component';

@Injectable({ providedIn: 'root' })
export class ModeRegleResolve implements Resolve<IModeRegle> {
  constructor(private service: ModeRegleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IModeRegle> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((modeRegle: HttpResponse<ModeRegle>) => {
          if (modeRegle.body) {
            return of(modeRegle.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ModeRegle());
  }
}

export const modeRegleRoute: Routes = [
  {
    path: '',
    component: ModeRegleComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.modeRegle.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ModeRegleDetailComponent,
    resolve: {
      modeRegle: ModeRegleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.modeRegle.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ModeRegleUpdateComponent,
    resolve: {
      modeRegle: ModeRegleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.modeRegle.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ModeRegleUpdateComponent,
    resolve: {
      modeRegle: ModeRegleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.modeRegle.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
