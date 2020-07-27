import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';

import { FakeTrade } from '../domain/faketrade';
import { FakeTradesApiService } from '../fake-trades-api.service';

@Component({
  selector: 'create-trade',
  templateUrl: './create-trade.component.html',
  styleUrls: ['./create-trade.component.css']
})
export class CreateTradeComponent {

    constructor(private router: Router,
		private api: FakeTradesApiService) {}
    
    create(a: string): void {
	var trade : FakeTrade = {amount: a, tradeid: 0};
	let tradeid: number = this.api.create(trade);
	if (tradeid > 0) {
	    this.router.navigate([`/tradecreatedconfirmation/${tradeid}`]);
	} else {
	    this.router.navigate([`/tradecreationfailure`]);
	}
    }
}
