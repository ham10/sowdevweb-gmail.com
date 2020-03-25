import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
// import { errorRoute } from './layouts/error/error.route';
// import { navbarRoute } from './layouts/navbar/navbar.route';
// import { DEBUG_INFO_ENABLED } from 'app/app.constants';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ConnexionComponent } from 'app/connexion/connexion.component';

// const LAYOUT_ROUTES = [navbarRoute, ...errorRoute];

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        // {path:'' , redirectTo: 'login' , pathMatch : 'full'},
        {
          path: '',
          component: ConnexionComponent,
          data: {
            authorities: [],
            pageTitle: 'Connexion'
          }
        },

        {
          path: 'menu',
          loadChildren: () => import('./menu/menu.module').then(m => m.MenuModule)
        },
        {
          path: 'admin',
          data: {
            authorities: ['ROLE_ADMIN']
          },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import('./admin/admin-routing.module').then(m => m.AdminRoutingModule)
        },
        {
          path: 'account',
          loadChildren: () => import('./account/account.module').then(m => m.AccountModule)
        }

        // ...LAYOUT_ROUTES
      ],

      { enableTracing: false }
    )
  ],
  exports: [RouterModule]
})
export class HpdAppRoutingModule {}
