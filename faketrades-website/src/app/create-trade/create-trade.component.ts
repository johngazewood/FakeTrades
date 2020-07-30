import { Component, OnInit, Input } from '@angular/core';
import { Observable, of } from 'rxjs';

import { FakeTrade } from '../domain/faketrade';
import { FakeTradesApiService } from '../fake-trades-api.service';

@Component({
  selector: 'create-trade',
  templateUrl: './create-trade.component.html',
  styleUrls: ['./create-trade.component.css']
})
export class CreateTradeComponent {
    tradeid: number;
    constructor(private api: FakeTradesApiService) {}
    
    create(a: string): void {
	var trade : FakeTrade = {amount: a, tradeid: 0};
	let resp: Observable<any> = this.api.create(trade);
	resp.subscribe(r => this.tradeid = r.tradeid);
	console.log('create-trade.component.ts>> tradeid: ' + this.tradeid);
    }
}
