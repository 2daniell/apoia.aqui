import { Component, signal, effect, computed } from '@angular/core';
import { Header } from '../header/header';
import { CampaignService } from '../../services/campaign/campaign-service';
import { CampaignModel } from '../../model/campaign-model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [Header],
  templateUrl: './dashboard.html'
})
export class Dashboard {

  private readonly service: CampaignService;

  public campaigns = signal<CampaignModel[]>([]);

  public campaignProgress = computed(() => {
    return this.campaigns().map(c => {
      const raised = parseFloat(c.raised);
      const goal = parseFloat(c.goal);

      const progress = goal === 0 ? 0 : Math.min(100, Math.round((raised / goal) * 100));

      return {
        ...c,
        progress
      };
    });
  });

  public constructor(service: CampaignService) {
    this.service = service;

    effect(() => {
      this.service.getAllCampaigns().subscribe({
        next: (data) => this.campaigns.set(data),
        error: (err) => console.error('Erro ao carregar campanhas', err)
      });
    });
  }
}
