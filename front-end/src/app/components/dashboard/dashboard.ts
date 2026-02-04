import { Component, signal, effect, computed, inject } from '@angular/core';
import { Header } from '../header/header';
import { CampaignService } from '../../services/campaign/campaign-service';
import { CampaignModel } from '../../model/campaign-model';
import { AuthService } from '../../services/auth/auth-service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [Header],
  templateUrl: './dashboard.html'
})
export class Dashboard {

  private auth = inject(AuthService)
  public user = this.auth.user;
}
