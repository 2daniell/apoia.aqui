import { Component, computed, effect, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Header } from '../header/header';
import { CampaignModel } from '../../model/campaign-model';
import { CampaignService } from '../../services/campaign/campaign-service';

@Component({
  selector: 'app-campaign',
  imports: [Header],
  templateUrl: './campaign.html',
  styleUrl: './campaign.css',
})
export class Campaign {

  private readonly activatedRoute: ActivatedRoute;
  private readonly service: CampaignService
  private readonly campaignId: string | null = null;

  public campaign = signal<CampaignModel | null>(null);
  public hasError = signal<boolean>(false);

  public progress = computed(() => {

    const c = this.campaign();

    if (!c) return 0;

    const raised = parseFloat(c.raised);
    const goal = parseFloat(c.goal);

    return Math.min(100, Math.round((raised / goal) * 100));
  })

  public campaignDaysAgo = computed(() => {

    const createdAt = this.campaign()?.createdAt;

    const createdDate = new Date(createdAt!);
    const now = new Date();

    const diffTime = now.getTime() - createdDate.getTime();
    const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24));

    if (diffDays === 0) return 'Criado hoje';
    if (diffDays === 1) return 'Criado há 1 dia atrás';
    return `Criado há ${diffDays} dias atrás`;
  })

  public constructor(activatedRoute: ActivatedRoute, service: CampaignService) {
    this.activatedRoute = activatedRoute;
    this.campaignId = this.activatedRoute.snapshot.paramMap.get('id');
    this.service = service;
    
    effect(() => {
      if (this.campaignId) {
        this.service.getCampaignById(this.campaignId!).subscribe({
          next: (c: CampaignModel) => this.campaign.set(c),
          error: () => this.hasError.set(true)
        })
      }
    })
  }

}
