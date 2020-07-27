import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TradeCreationFailureComponent } from './trade-creation-failure.component';

describe('TradeCreationFailureComponent', () => {
  let component: TradeCreationFailureComponent;
  let fixture: ComponentFixture<TradeCreationFailureComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TradeCreationFailureComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TradeCreationFailureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
