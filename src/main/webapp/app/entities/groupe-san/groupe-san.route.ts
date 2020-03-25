import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGroupeSan, GroupeSan } from 'app/shared/model/groupe-san.model';
import { GroupeSanService } from './groupe-san.service';
import { GroupeSanComponent } from './groupe-san.component';
import { GroupeSanDetailComponent } from './groupe-san-detail.component';
import { GroupeSanUpdateComponent } from './groupe-san-update.component';

@Injectable({ providedIn: 'root' })
export class GroupeSanResolve implements Resolve<IGroupeSan> {
  constructor(private service: GroupeSanService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGroupeSan> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((groupeSan: HttpResponse<GroupeSan>) => {
          if (groupeSan.body) {
            return of(groupeSan.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GroupeSan());
  }
}

export const groupeSanRoute: Routes = [
  {
    path: '',
    component: GroupeSanComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.groupeSan.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GroupeSanDetailComponent,
    resolve: {
      groupeSan: GroupeSanResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.groupeSan.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GroupeSanUpdateComponent,
    resolve: {
      groupeSan: GroupeSanResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.groupeSan.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GroupeSanUpdateComponent,
    resolve: {
      groupeSan: GroupeSanResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.groupeSan.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
