import { Component, inject, signal } from '@angular/core';
import { Header } from '../header/header';
import { CampaignService } from '../../services/campaign/campaign-service';
import { CampaignModel } from '../../model/campaign-model';
import { CampaignCard } from '../campaign-card/campaign-card';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [Header, CampaignCard, RouterLink,],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {

  private service: CampaignService = inject(CampaignService);

  public campaigns = signal<CampaignModel[]>([]);

  public ngOnInit() {
    this.loadFeatured();
  }

  public scrollTo(elementId: string): void {
    const element = document.getElementById(elementId);
    if (element) {
      element.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  }

  private loadFeatured() {
    this.service.getCampaigns().subscribe({
      next: (data) => {
        this.campaigns.set(data)
      },
      error: (err) => {
        console.log("Erro (err1)", err)
      }
    })
  }

}
