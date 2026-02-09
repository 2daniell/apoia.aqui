import { CommonModule } from '@angular/common';
import { Component, effect, input, output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CampaignRequest } from '../../model/campaign-model';

@Component({
  selector: 'campaign-form',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './campaign-form.html',
  styleUrl: './campaign-form.css',
})
export class CampaignForm {

  public form: FormGroup;

  public formSubmit = output<CampaignRequest>();

  public campaign = input<CampaignRequest | null>(null);

  public constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      title: ['', [Validators.required]],
      description: ['', [Validators.required]],
      goal: [0, [Validators.required, Validators.min(1)]]
    });

    effect(() => {
      const data = this.campaign();

      if (data) {
        this.form.patchValue(data);
      }
    })
  }

  public onSubmit() {
    if (this.form.invalid) return;

    const data: CampaignRequest = this.form.getRawValue();

    this.formSubmit.emit(data);
  }

}
