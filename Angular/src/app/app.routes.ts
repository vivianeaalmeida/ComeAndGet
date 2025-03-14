import { Routes } from '@angular/router';
import { HomeComponent } from './Components/home/home.component';
import { LoginComponent } from './Components/login/login.component';
import { RegisterComponent } from './Components/register/register.component';
import { CreateAdvComponent } from './Components/create-adv/create-adv.component';
import { NotFoundComponent } from './Components/not-found/not-found.component';
import { UserAreaComponent } from './Components/user-area/user-area.component';
import { clientGuard } from './Guards/client.guard';
import { AdvlistingComponent } from './Components/advlisting/advlisting.component';
import { DashboardComponent } from './Components/dashboard/dashboard.component';
import { CategoriesDashboardComponent } from './Components/categories-dashboard/categories-dashboard.component';
import { AdvertisementsDashboardComponent } from './Components/advertisements-dashboard/advertisements-dashboard.component';
import { TipsDashboardComponent } from './Components/tips-dashboard/tips-dashboard.component';
import { ReservationAttemptListComponent } from './Components/reservation-attempt-list/reservation-attempt-list.component';
import { TipsListingComponent } from './Components/tips-listing/tips-listing.component';
import { PublicTipsListingComponent } from './Components/public-tips-listing/public-tips-listing.component';
import { managerGuard } from './Guards/manager.guard';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  {
    path: 'user-area',
    component: UserAreaComponent,
    canActivate: [clientGuard],
  },
  { path: 'register', component: RegisterComponent },
  {
    path: 'create-adv',
    component: CreateAdvComponent,
    canActivate: [clientGuard],
  },

  { path: 'view-active', component: AdvlistingComponent }, // Lista dos users
  // CRIAR: path> 'view-all' ---> Lista dos admins
  { path: 'dashboard', component: DashboardComponent,
    canActivate: [managerGuard],
   },
  { path: 'dashboard/advertisements', component: AdvertisementsDashboardComponent,
    canActivate: [managerGuard],
   },
  { path: 'dashboard/categories', component: CategoriesDashboardComponent,
    canActivate: [managerGuard],
   },
  { path: 'dashboard/tips', component: TipsDashboardComponent,
    canActivate: [managerGuard],
   },
  { path: 'advertisements', component: AdvlistingComponent,
    canActivate: [managerGuard],
   },
  { path: 'reservation-attempts', component: ReservationAttemptListComponent},
  { path: 'blog-tips', component: TipsListingComponent},
  { path: 'tips-blog', component: PublicTipsListingComponent},
  { path: '**', component: NotFoundComponent },
];
