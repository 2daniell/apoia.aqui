import { Component, inject } from '@angular/core';
import { CampaignForm } from '../campaign-form/campaign-form';
import { Header } from '../header/header';
import { Router, RouterLink } from '@angular/router';
import { CampaignRequest } from '../../model/campaign-model';
import { CampaignService } from '../../services/campaign/campaign-service';

@Component({
  selector: 'app-create-campaign',
  imports: [CampaignForm, Header, RouterLink],
  templateUrl: './create-campaign.html',
  styleUrl: './create-campaign.css',
})
export class CreateCampaign {

  private service = inject(CampaignService);
  private router = inject(Router);

  public create(data: CampaignRequest) {

    console.log(data)

    this.service.createCampaign(data).subscribe({
      next: () => this.router.navigate(["/app/dashboard"]),
      error: () => window.alert("Ocorreu um erro"),
    })
  }

}
