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

    creationEndpoint: string;

    hostname: string;

    constructor(private http: HttpClient) {
        this.hostname = window.location.hostname;
        this.apiEndpoint = 'http://' + this.hostname + ':' + environment.apiPort + environment.apiPath;
        this.creationEndpoint = this.apiEndpoint + '/trade/create';
    }

    checkHeartbeat(): boolean {
        var heart: Observable<any> = this.http.get<any>(this.apiEndpoint + '/heartbeat');
        var ok: boolean = false;
        var resp;
        heart.subscribe({
            next: x => { resp = { heartbeat: (x as any).heartbeat } },
            complete: () => {
                console.log('fake-trades-api.service.ts>> resp: ' + resp.heartbeat);
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

        var create: Observable<any> = this.http.post<any>(this.creationEndpoint, trade, this.httpOptions);
        var resp;
        var finished: boolean = false;
        create.subscribe({
            next: x => { resp = x },
            error: x => { console.error(x); finished = true; },
            complete: () => {
                console.log('fake-trades-api.service.ts>> new tradeid: ' + resp.tradeid);
                tradeid = resp.tradeid;
                finished = true;
            },
        });
        console.log('fake-trades-api.service.ts>> 1 are we finished? ' + finished);
        setTimeout(() => console.log('fake-trades-api.service.ts>> timeout.'), 1000);
        console.log('fake-trades-api.service.ts>> 2 are we finished? ' + finished);

        return tradeid;
    }
    observableDemonstration(): void {
        const observable = of(1, 2, 3);
        const observer = {
            next: x => console.log('fake-trades-api.service.ts>> value: ' + x),
            error: err => console.log('fake-trades-api.service.ts>> error: ' + err),
            complete: () => console.log('fake-trades-api.service.ts>> complete.'),
        };
        observable.subscribe(observer);
    }


}
