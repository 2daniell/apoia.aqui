import { Component, inject, signal } from '@angular/core';
import { CampaignForm } from '../campaign-form/campaign-form';
import { CampaignModel, CampaignRequest } from '../../model/campaign-model';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Header } from '../header/header';
import { CampaignService } from '../../services/campaign/campaign-service';

@Component({
  selector: 'app-edit-campaign',
  imports: [CampaignForm, RouterLink, Header],
  templateUrl: './edit-campaign.html',
  styleUrl: './edit-campaign.css',
})
export class EditCampaign {

  private route = inject(ActivatedRoute)
  private router = inject(Router)
  private service = inject(CampaignService);

  public campaign = signal<CampaignModel | null>(null);

  public ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id')!;

    this.service.getCampaignById(id).subscribe(res => {

      if (!res.isOwner) {
        this.router.navigate(["/unauthorized"])
        return;
      }

      this.campaign.set(res);
    });
  }

  public update(data: CampaignRequest) {
    const id = this.route.snapshot.paramMap.get('id')!;

    this.service.updateCampaign(id, data).subscribe(() => {
      this.router.navigate(['/app/dashboard']);
    });
  }

}
