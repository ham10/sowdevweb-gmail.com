import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IClasseProd, ClasseProd } from 'app/shared/model/classe-prod.model';
import { ClasseProdService } from './classe-prod.service';
import { ClasseProdComponent } from './classe-prod.component';
import { ClasseProdDetailComponent } from './classe-prod-detail.component';
import { ClasseProdUpdateComponent } from './classe-prod-update.component';

@Injectable({ providedIn: 'root' })
export class ClasseProdResolve implements Resolve<IClasseProd> {
  constructor(private service: ClasseProdService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClasseProd> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((classeProd: HttpResponse<ClasseProd>) => {
          if (classeProd.body) {
            return of(classeProd.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ClasseProd());
  }
}

export const classeProdRoute: Routes = [
  {
    path: '',
    component: ClasseProdComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.classeProd.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ClasseProdDetailComponent,
    resolve: {
      classeProd: ClasseProdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.classeProd.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ClasseProdUpdateComponent,
    resolve: {
      classeProd: ClasseProdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.classeProd.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ClasseProdUpdateComponent,
    resolve: {
      classeProd: ClasseProdResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.classeProd.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
