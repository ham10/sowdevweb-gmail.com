import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISchemaCompta, SchemaCompta } from 'app/shared/model/schema-compta.model';
import { SchemaComptaService } from './schema-compta.service';
import { SchemaComptaComponent } from './schema-compta.component';
import { SchemaComptaDetailComponent } from './schema-compta-detail.component';
import { SchemaComptaUpdateComponent } from './schema-compta-update.component';

@Injectable({ providedIn: 'root' })
export class SchemaComptaResolve implements Resolve<ISchemaCompta> {
  constructor(private service: SchemaComptaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISchemaCompta> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((schemaCompta: HttpResponse<SchemaCompta>) => {
          if (schemaCompta.body) {
            return of(schemaCompta.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SchemaCompta());
  }
}

export const schemaComptaRoute: Routes = [
  {
    path: '',
    component: SchemaComptaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.schemaCompta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SchemaComptaDetailComponent,
    resolve: {
      schemaCompta: SchemaComptaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.schemaCompta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SchemaComptaUpdateComponent,
    resolve: {
      schemaCompta: SchemaComptaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.schemaCompta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SchemaComptaUpdateComponent,
    resolve: {
      schemaCompta: SchemaComptaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.schemaCompta.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
