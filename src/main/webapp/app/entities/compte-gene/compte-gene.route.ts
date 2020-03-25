import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICompteGene, CompteGene } from 'app/shared/model/compte-gene.model';
import { CompteGeneService } from './compte-gene.service';
import { CompteGeneComponent } from './compte-gene.component';
import { CompteGeneDetailComponent } from './compte-gene-detail.component';
import { CompteGeneUpdateComponent } from './compte-gene-update.component';

@Injectable({ providedIn: 'root' })
export class CompteGeneResolve implements Resolve<ICompteGene> {
  constructor(private service: CompteGeneService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompteGene> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((compteGene: HttpResponse<CompteGene>) => {
          if (compteGene.body) {
            return of(compteGene.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CompteGene());
  }
}

export const compteGeneRoute: Routes = [
  {
    path: '',
    component: CompteGeneComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.compteGene.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CompteGeneDetailComponent,
    resolve: {
      compteGene: CompteGeneResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.compteGene.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CompteGeneUpdateComponent,
    resolve: {
      compteGene: CompteGeneResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.compteGene.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CompteGeneUpdateComponent,
    resolve: {
      compteGene: CompteGeneResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'hpdApp.compteGene.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
