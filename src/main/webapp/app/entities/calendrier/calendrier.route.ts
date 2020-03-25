import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICalendrier, Calendrier } from 'app/shared/model/calendrier.model';
import { CalendrierService } from './calendrier.service';
import { CalendrierComponent } from './calendrier.component';
import { CalendrierDetailComponent } from './calendrier-detail.component';
import { CalendrierUpdateComponent } from './calendrier-update.component';

@Injectable({ providedIn: 'root' })
export class CalendrierResolve implements Resolve<ICalendrier> {
  constructor(private service: CalendrierService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICalendrier> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((calendrier: HttpResponse<Calendrier>) => {
          if (calendrier.body) {
            return of(calendrier.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Calendrier());
  }
}

export const calendrierRoute: Routes = [
  {
    path: '',
    component: CalendrierComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.calendrier.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CalendrierDetailComponent,
    resolve: {
      calendrier: CalendrierResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.calendrier.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CalendrierUpdateComponent,
    resolve: {
      calendrier: CalendrierResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.calendrier.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CalendrierUpdateComponent,
    resolve: {
      calendrier: CalendrierResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.calendrier.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
