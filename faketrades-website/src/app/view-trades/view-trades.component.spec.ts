import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewTradesComponent } from './view-trades.component';

describe('ViewTradesComponent', () => {
  let component: ViewTradesComponent;
  let fixture: ComponentFixture<ViewTradesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewTradesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewTradesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
