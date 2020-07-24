import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { ViewTradesComponent } from './view-trades/view-trades.component';
import { AppRoutingModule } from './app-routing.module';
import { CreateTradeComponent } from './create-trade/create-trade.component';

@NgModule({
  declarations: [
    AppComponent,
    ViewTradesComponent,
    CreateTradeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
