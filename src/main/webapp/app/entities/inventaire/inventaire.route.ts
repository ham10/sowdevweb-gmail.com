import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInventaire, Inventaire } from 'app/shared/model/inventaire.model';
import { InventaireService } from './inventaire.service';
import { InventaireComponent } from './inventaire.component';
import { InventaireDetailComponent } from './inventaire-detail.component';
import { InventaireUpdateComponent } from './inventaire-update.component';

@Injectable({ providedIn: 'root' })
export class InventaireResolve implements Resolve<IInventaire> {
  constructor(private service: InventaireService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInventaire> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((inventaire: HttpResponse<Inventaire>) => {
          if (inventaire.body) {
            return of(inventaire.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Inventaire());
  }
}

export const inventaireRoute: Routes = [
  {
    path: '',
    component: InventaireComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.inventaire.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InventaireDetailComponent,
    resolve: {
      inventaire: InventaireResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.inventaire.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InventaireUpdateComponent,
    resolve: {
      inventaire: InventaireResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.inventaire.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InventaireUpdateComponent,
    resolve: {
      inventaire: InventaireResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.inventaire.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
