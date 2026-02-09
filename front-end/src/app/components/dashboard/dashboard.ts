import { Component, signal, effect, computed, inject } from '@angular/core';
import { Header } from '../header/header';
import { CampaignService } from '../../services/campaign/campaign-service';
import { CampaignModel } from '../../model/campaign-model';
import { AuthService } from '../../services/auth/auth-service';
import { DashboardStats } from '../../model/dashboard-model';
import { CampaignCard } from '../campaign-card/campaign-card';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [Header, CampaignCard, RouterLink],
  templateUrl: './dashboard.html'
})
export class Dashboard {

  private auth = inject(AuthService)
  private service = inject(CampaignService)

  public user = this.auth.user;
  public stats = signal<DashboardStats | null>(null);
  public userCampaigns = signal<CampaignModel[]>([])
  public campaigns = signal<CampaignModel[]>([]);

  public ngOnInit() {
    this.service.getDashboardStats().subscribe({ next: (value) => {
      this.stats.set(value)
    } })

    this.service.getUserCampaigns().subscribe({ next: (value) => {
      this.userCampaigns.set(value);
    },})

    this.service.getCampaigns().subscribe({ next: (value) => {
      this.campaigns.set(value);
    }})
  }

  public deleteCampaign(id: string) {
    
    const confirm = window.confirm("Tem certeza que deseja excluir essa campanha?")

    if (!confirm) return;

    this.service.deleteCampaign(id).subscribe({

      next: () => {

        this.userCampaigns.update(campaigns => campaigns.filter(c => c.id !== id));

        this.service.getDashboardStats().subscribe({
          next: stats => this.stats.set(stats)
        });
      },

      error: () => window.alert("Ocorreu um erro ao deletar campanha!")
    })


  }
}
