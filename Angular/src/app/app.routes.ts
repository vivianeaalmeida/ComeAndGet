import { Routes } from '@angular/router';
import { HomeComponent } from './Components/home/home.component';
import { LoginComponent } from './Components/login/login.component';
import { RegisterComponent } from './Components/register/register.component';
import { CreateAdvComponent } from './Components/create-adv/create-adv.component';
import { NotFoundComponent } from './Components/not-found/not-found.component';
import { CategoriesDashboardComponent } from './Components/categories-dashboard/categories-dashboard.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'createadv', component: CreateAdvComponent },
  { path: 'dashboard', component: CategoriesDashboardComponent },
  { path: '**', component: NotFoundComponent },
];
