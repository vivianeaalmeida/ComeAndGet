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
  { path: 'dashboard', component: DashboardComponent },
  { path: 'categoriesDash', component: CategoriesDashboardComponent },
  { path: 'advertisementsDash', component: AdvertisementsDashboardComponent },
  { path: 'categoriesDash', component: CategoriesDashboardComponent },
  { path: 'tipsDash', component: TipsDashboardComponent },
  { path: 'advertisements', component: AdvlistingComponent },
  { path: 'reservation-attempts', component: ReservationAttemptListComponent},
  { path: 'blog-tips', component: TipsListingComponent},
  { path: '**', component: NotFoundComponent },
];
