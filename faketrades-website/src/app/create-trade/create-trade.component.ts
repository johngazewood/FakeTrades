import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';

import { Trade } from '../domain/trade';

@Component({
  selector: 'create-trade',
  templateUrl: './create-trade.component.html',
  styleUrls: ['./create-trade.component.css']
})
export class CreateTradeComponent {

    constructor(private router: Router) {}
    
    create(amount: string): void {
	console.log('create new trade, and reroute to trade-created-confirmation, amount:  ' + amount);
	let tradeid: number = this.callDao(amount);
	if (tradeid > 0) {
	    this.router.navigate([`/tradecreatedconfirmation/${tradeid}`]);
	} else {
	    console.log('please, reroute to trade-creation-failure page.');
	}
    }

    callDao(amount: string): number {
	return 6;
    }
}
