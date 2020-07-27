import { TestBed } from '@angular/core/testing';

import { FakeTradesApiService } from './fake-trades-api.service';

describe('FakeTradesApiService', () => {
  let service: FakeTradesApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FakeTradesApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
