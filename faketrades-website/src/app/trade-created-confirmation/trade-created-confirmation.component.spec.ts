import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TradeCreatedConfirmationComponent } from './trade-created-confirmation.component';

describe('TradeCreatedConfirmationComponent', () => {
  let component: TradeCreatedConfirmationComponent;
  let fixture: ComponentFixture<TradeCreatedConfirmationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TradeCreatedConfirmationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TradeCreatedConfirmationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
