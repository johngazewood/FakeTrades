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
        this.hostname = window.location.hostname;
        this.apiEndpoint = 'http://' + this.hostname + ':' + environment.apiPort + environment.apiPath;
    }

    checkHeartbeat(): boolean {
        var heart: Observable<any> = this.http.get<any>(this.apiEndpoint + '/heartbeat');
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
        this.checkHeartbeat()
        var create: Observable<any> = this.http.post<any>(this.apiEndpoint + '/trade/create', trade, this.httpOptions);
        return create;
    }

    view(): Observable<FakeTrade[]> {
	return this.http.get<any>(this.apiEndpoint + '/trade/view', this.httpOptions);
    }
}
