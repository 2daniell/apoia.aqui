import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CampaignService } from '../../services/campaign/campaign-service';
import { Header } from "../header/header";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, Header],
  templateUrl: './dashboard.html'
})
export class Dashboard implements OnInit {

  campaigns: any[] = [];

  constructor(private campaignService: CampaignService) {}

  ngOnInit(): void {
    this.loadCampaigns();
  }

  loadCampaigns() {
    this.campaignService.getAllCampaigns().subscribe({
      next: (data) => {
        this.campaigns = data;
        console.log('Campanhas:', data);
      },
      error: (err) => console.error('Erro ao carregar campanhas', err)
    });
  }

}
