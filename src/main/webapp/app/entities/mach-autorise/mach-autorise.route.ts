import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMachAutorise, MachAutorise } from 'app/shared/model/mach-autorise.model';
import { MachAutoriseService } from './mach-autorise.service';
import { MachAutoriseComponent } from './mach-autorise.component';
import { MachAutoriseDetailComponent } from './mach-autorise-detail.component';
import { MachAutoriseUpdateComponent } from './mach-autorise-update.component';

@Injectable({ providedIn: 'root' })
export class MachAutoriseResolve implements Resolve<IMachAutorise> {
  constructor(private service: MachAutoriseService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMachAutorise> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((machAutorise: HttpResponse<MachAutorise>) => {
          if (machAutorise.body) {
            return of(machAutorise.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MachAutorise());
  }
}

export const machAutoriseRoute: Routes = [
  {
    path: '',
    component: MachAutoriseComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.machAutorise.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MachAutoriseDetailComponent,
    resolve: {
      machAutorise: MachAutoriseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.machAutorise.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MachAutoriseUpdateComponent,
    resolve: {
      machAutorise: MachAutoriseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.machAutorise.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MachAutoriseUpdateComponent,
    resolve: {
      machAutorise: MachAutoriseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.machAutorise.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
