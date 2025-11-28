import { Routes } from '@angular/router';
import { Home } from './components/home/home';
import { Login } from './components/login/login';
import { Campaign } from './components/campaign/campaign';

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'login', component: Login },
  { path: 'campaign/:id', component: Campaign },
  { path: 'campaign', redirectTo: '/' }
];
