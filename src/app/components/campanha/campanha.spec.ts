import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Campanha } from './campanha';

describe('Campanha', () => {
  let component: Campanha;
  let fixture: ComponentFixture<Campanha>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Campanha]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Campanha);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
