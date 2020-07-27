import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FakeTrade } from './domain/faketrade';

@Injectable({
  providedIn: 'root'
})
export class FakeTradesApiService {

    httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json'})
    };
    
    apiEndpoint: string;
    
    creationEndpoint: string;
    
    constructor(private http: HttpClient) {
	this.apiEndpoint = 'http://localhost:8080/faketrades-api';
	this.creationEndpoint = this.apiEndpoint+'/trade/create';
    }

    checkHeartbeat(): boolean {
	var heart : Observable<any> = this.http.get<any>(this.apiEndpoint+'/heartbeat');
	var ok: boolean = false; 
	var resp;
	heart.subscribe({
	    next: x => {resp = {heartbeat: (x as any).heartbeat}},
	    complete: () => {console.log('fake-trades-api.service.ts>> resp: ' + resp.heartbeat);
			     if ("The heart beats back. Thump." == resp.heartbeat) {
				 console.log('fake-trades-api.service.ts>> heartbeat is OK');
				 ok = true;
			     }
			    },
	});
	console.log('fake-trades-api.service.ts>> check heartbeat returning: ' + ok);
	return ok;
    }
    

    create(trade: FakeTrade): number {
	this.checkHeartbeat()
	
	var tradeid = -1;
	
	var create : Observable<any> = this.http.post<any>(this.creationEndpoint, trade, this.httpOptions);
	var resp;
	create.subscribe({
	    next: x => {resp = x},
	    complete: () => {console.log('fake-trades-api.service.ts>> new tradeid: ' + resp.tradeid);
			     tradeid=resp.tradeid},
	});

	
	return tradeid;
    }


    observableDemonstration(): void {
	const observable = of(1,2,3);
	const observer = {
	    next: x => console.log('fake-trades-api.service.ts>> value: ' + x),
	    error: err => console.log('fake-trades-api.service.ts>> error: ' + err),
	    complete: () => console.log('fake-trades-api.service.ts>> complete.'),
	};
	observable.subscribe(observer);
    }
    
    
}
