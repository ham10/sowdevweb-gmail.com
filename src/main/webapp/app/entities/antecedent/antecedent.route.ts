import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAntecedent, Antecedent } from 'app/shared/model/antecedent.model';
import { AntecedentService } from './antecedent.service';
import { AntecedentComponent } from './antecedent.component';
import { AntecedentDetailComponent } from './antecedent-detail.component';
import { AntecedentUpdateComponent } from './antecedent-update.component';

@Injectable({ providedIn: 'root' })
export class AntecedentResolve implements Resolve<IAntecedent> {
  constructor(private service: AntecedentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAntecedent> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((antecedent: HttpResponse<Antecedent>) => {
          if (antecedent.body) {
            return of(antecedent.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Antecedent());
  }
}

export const antecedentRoute: Routes = [
  {
    path: '',
    component: AntecedentComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.antecedent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AntecedentDetailComponent,
    resolve: {
      antecedent: AntecedentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.antecedent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AntecedentUpdateComponent,
    resolve: {
      antecedent: AntecedentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.antecedent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AntecedentUpdateComponent,
    resolve: {
      antecedent: AntecedentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.antecedent.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
