import { Routes } from '@angular/router';
import { Home } from './components/home/home';
import { Login } from './components/login/login';
import { Campaign } from './components/campaign/campaign';
import { Cadastro } from './components/cadastro/cadastro';
import { Dashboard } from './components/dashboard/dashboard';

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'login', component: Login },
  { path: 'campaign/:id', component: Campaign },
  { path: 'campaign', redirectTo: '/' },
  { path: 'cadastro', component: Cadastro },
  { path: 'dashboard', component: Dashboard },

];
