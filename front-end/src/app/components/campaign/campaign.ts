import { Component, computed, inject, signal } from '@angular/core';
import { Header } from '../header/header';
import { CampaignModel } from '../../model/campaign-model';
import { CampaignService } from '../../services/campaign/campaign-service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule, Location } from '@angular/common';

@Component({
  selector: 'app-campaign',
  imports: [Header, CommonModule],
  templateUrl: './campaign.html',
  styleUrl: './campaign.css',
})
export class Campaign {

  private route = inject(ActivatedRoute);

  private campaignService = inject(CampaignService);

  private location = inject(Location)

  private router = inject(Router)

  public campaign = signal<CampaignModel | null>(null);
  public percentage = computed(() => {

    const c = this.campaign();
    if (!c) return 0;

    const raised = c.raised;
    const goal = c.goal;

    return Math.min((raised / goal) * 100, 100);
  });

  public formattedDate = computed(() => {
    const c = this.campaign();
    if (!c?.createdAt) return "Indefinido";

    return new Intl.DateTimeFormat('pt-BR', {
      day: 'numeric',
      month: 'long',
      year: 'numeric'
    }).format(new Date(c.createdAt));
  });

  public ngOnInit() {

    const id = this.route.snapshot.paramMap.get('id');

    if (!id) return;

    this.load(id);
  }

  public back() {
    if (window.history.length > 1) {
      this.location.back();
    } else {
      this.router.navigate(['/']);
    }
  }

  private load(id: string) {

    this.campaignService.getCampaignById(id).subscribe(c => {
      this.campaign.set(c);
    });

  }

}
