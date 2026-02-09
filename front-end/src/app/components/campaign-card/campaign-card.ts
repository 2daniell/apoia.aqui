import { Component, computed, input, output } from '@angular/core';
import { CampaignModel } from '../../model/campaign-model';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'campaign-card',
  imports: [CommonModule, RouterLink],
  templateUrl: './campaign-card.html',
  styleUrl: './campaign-card.css',
})
export class CampaignCard {

  public campaign = input.required<CampaignModel>();

  public showActions = input<boolean>(false);

  public delete = output<string>();

  public raised = computed(() =>
    this.campaign().raised
  );

  public goal = computed(() =>
    this.campaign().goal
  );

  public percentage = computed(() => {
    const value = (this.raised() / this.goal()) * 100;
    return Math.min(value, 100);
  });

  public isOwner = computed(() => this.campaign().isOwner);

  public onDelete() {
    this.delete.emit(this.campaign().id)
  }
}
