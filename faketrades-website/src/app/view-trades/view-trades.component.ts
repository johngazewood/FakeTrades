import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';

import { FakeTrade } from '../domain/faketrade';
import { FakeTradesApiService } from '../fake-trades-api.service';

@Component({
  selector: 'view-trades',
  templateUrl: './view-trades.component.html',
  styleUrls: ['./view-trades.component.css']
})
export class ViewTradesComponent implements OnInit {

    trades: FakeTrade[];
    
    constructor(private api: FakeTradesApiService) {
	this.getTrades();
    }

    getTrades() {
	let resp: Observable<FakeTrade[]> = this.api.view();
	resp.subscribe(r => this.trades = r)
    }
    
    reload(): void {
	this.getTrades();
    }
    
  ngOnInit(): void {
  }

}
