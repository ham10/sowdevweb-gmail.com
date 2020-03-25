import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFormeProd, FormeProd } from 'app/shared/model/forme-prod.model';
import { FormeProdService } from './forme-prod.service';
import { FormeProdComponent } from './forme-prod.component';
import { FormeProdDetailComponent } from './forme-prod-detail.component';
import { FormeProdUpdateComponent } from './forme-prod-update.component';

@Injectable({ providedIn: 'root' })
export class FormeProdResolve implements Resolve<IFormeProd> {
  constructor(private service: FormeProdService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFormeProd> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((formeProd: HttpResponse<FormeProd>) => {
          if (formeProd.body) {
            return of(formeProd.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FormeProd());
  }
}

export const formeProdRoute: Routes = [
  {
    path: '',
    component: FormeProdComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.formeProd.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FormeProdDetailComponent,
    resolve: {
      formeProd: FormeProdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.formeProd.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FormeProdUpdateComponent,
    resolve: {
      formeProd: FormeProdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.formeProd.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FormeProdUpdateComponent,
    resolve: {
      formeProd: FormeProdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.formeProd.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
