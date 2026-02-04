import { Component, computed, inject, signal } from '@angular/core';
import { Header } from '../header/header';
import { CampaignService } from '../../services/campaign/campaign-service';
import { CampaignModel } from '../../model/campaign-model';
import { CampaignCard } from '../campaign-card/campaign-card';

@Component({
  selector: 'app-explore',
  imports: [Header, CampaignCard],
  templateUrl: './explore.html',
  styleUrl: './explore.css',
})
export class Explore {

  private service = inject(CampaignService)

  public campaigns = signal<CampaignModel[]>([]);

  public search = signal<string>("")

  public filteredCampaigns = computed(() => {

    const search = this.search().toLowerCase();

    return this.campaigns().filter(c => {

      const matchesSearch =
        c.title.toLowerCase().includes(search) ||
        c.description.toLowerCase().includes(search);

      return matchesSearch
    });
  });

  public ngOnInit() {
    this.load();
  }

  public setSearch(value: string) {
    this.search.set(value);
  }

  public load() {
    this.service.getCampaigns(1, 50).subscribe(data => {
      this.campaigns.set(data);
    });
  }

}
