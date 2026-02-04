import { Component, computed, input } from '@angular/core';
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

  raised = computed(() =>
    this.parseValue(this.campaign().raised)
  );

  goal = computed(() =>
    this.parseValue(this.campaign().goal)
  );

  percentage = computed(() => {
    const value = (this.raised() / this.goal()) * 100;
    return Math.min(value, 100);
  });

  private parseValue(value: string): number {
    return parseFloat(
      value.replace('.', '').replace(',', '.')
    );
  }
}
