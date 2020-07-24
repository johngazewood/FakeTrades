import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'trade-created-confirmation',
  templateUrl: './trade-created-confirmation.component.html',
  styleUrls: ['./trade-created-confirmation.component.css']
})
export class TradeCreatedConfirmationComponent implements OnInit {

    constructor(private route: ActivatedRoute) { }

    ngOnInit(): void {
	this.getTrade();
    }

    getTrade(): void {
	const tradeid = +this.route.snapshot.paramMap.get('tradeid');
	console.log('please, actually get the trade for tradeid: ' + tradeid);
    }
}
