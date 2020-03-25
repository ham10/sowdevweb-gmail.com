import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypePiece, TypePiece } from 'app/shared/model/type-piece.model';
import { TypePieceService } from './type-piece.service';
import { TypePieceComponent } from './type-piece.component';
import { TypePieceDetailComponent } from './type-piece-detail.component';
import { TypePieceUpdateComponent } from './type-piece-update.component';

@Injectable({ providedIn: 'root' })
export class TypePieceResolve implements Resolve<ITypePiece> {
  constructor(private service: TypePieceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypePiece> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typePiece: HttpResponse<TypePiece>) => {
          if (typePiece.body) {
            return of(typePiece.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypePiece());
  }
}

export const typePieceRoute: Routes = [
  {
    path: '',
    component: TypePieceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePiece.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypePieceDetailComponent,
    resolve: {
      typePiece: TypePieceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePiece.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypePieceUpdateComponent,
    resolve: {
      typePiece: TypePieceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePiece.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypePieceUpdateComponent,
    resolve: {
      typePiece: TypePieceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.typePiece.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
