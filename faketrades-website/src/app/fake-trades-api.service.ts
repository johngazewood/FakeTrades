import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { environment } from '../environments/environment';
import { FakeTrade } from './domain/faketrade';

@Injectable({
    providedIn: 'root'
})
export class FakeTradesApiService {

    httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };

    apiEndpoint: string;

    hostname: string;

    constructor(private http: HttpClient) {
	if (environment.env === 'local') {
	    this.hostname = '0.0.0.0'; //window.location.hostname;
	} else {
	    this.hostname = environment.apiHost;
	}
        
        //this.apiEndpoint = 'http://' + this.hostname + ':' + environment.apiPort + environment.apiPath;\
	this.apiEndpoint = 'http://' + this.hostname + ':4200' + environment.apiPath;
    }

    checkHeartbeat(): boolean {
        //var heart: Observable<any> = this.http.get<any>(this.apiEndpoint + '/heartbeat');
	var heart: Observable<any> = this.http.get<any>(environment.apiPath+'/heartbeat');
        var ok: boolean = false;
        var resp;
	(async () => {
            await heart.subscribe({
		next: x => { resp = { heartbeat: (x as any).heartbeat } },
		complete: () => {
                    console.log('fake-trades-api.service.ts>> resp: ' + resp.heartbeat);
                    if ("The heart beats back. Thump." == resp.heartbeat) {
			console.log('fake-trades-api.service.ts>> heartbeat is OK');
			ok = true;
                    }
		},
            });
	})();
        console.log('fake-trades-api.service.ts>> check heartbeat returning: ' + ok);
        return ok;
    }


    create(trade: FakeTrade): Observable<any> {
	console.log('fake-trades-api.services.ts>> endpoint is: ' + this.apiEndpoint);
        this.checkHeartbeat()
        var create: Observable<any> = this.http.post<any>(environment.apiPath+'/trade/create', trade, this.httpOptions);
        return create;
    }

    view(): Observable<FakeTrade[]> {
	return this.http.get<any>(environment.apiPath + '/trade/view', this.httpOptions);
    }
}
