import { Routes } from '@angular/router';
import { Home } from './components/home/home';
import { Login } from './components/login/login';
import { Campaign } from './components/campaign/campaign';
import { Cadastro } from './components/cadastro/cadastro';
import { Dashboard } from './components/dashboard/dashboard';
import { Explore } from './components/explore/explore';
import { CreateCampaign } from './components/create-campaign/create-campaign';
import { authGuard } from './guard/auth-guard';
import { Unauthorized } from './components/unauthorized/unauthorized';
import { EditCampaign } from './components/edit-campaign/edit-campaign';

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'cadastro', component: Cadastro },
  { path: 'login', component: Login },
  { path: 'campaign/new', component: CreateCampaign, canActivate: [authGuard] },
  { path: 'campaign/edit/:id', component: EditCampaign, canActivate: [authGuard] },
  { path: 'campaign/:id', component: Campaign },
  { path: 'campaign', redirectTo: '/' },
  { path: 'explore', component: Explore },
  { path: 'app/dashboard', component: Dashboard, canActivate: [authGuard] },
  { path: 'unauthorized', component: Unauthorized },
];
