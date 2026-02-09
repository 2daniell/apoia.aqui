import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditCampaign } from './edit-campaign';

describe('EditCampaign', () => {
  let component: EditCampaign;
  let fixture: ComponentFixture<EditCampaign>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditCampaign]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditCampaign);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
