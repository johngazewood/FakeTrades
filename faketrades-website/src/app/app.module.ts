import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { ViewTradesComponent } from './view-trades/view-trades.component';
import { AppRoutingModule } from './app-routing.module';
import { CreateTradeComponent } from './create-trade/create-trade.component';
import { TradeCreatedConfirmationComponent } from './trade-created-confirmation/trade-created-confirmation.component';
import { TradeCreationFailureComponent } from './trade-creation-failure/trade-creation-failure.component';

@NgModule({
  declarations: [
    AppComponent,
    ViewTradesComponent,
    CreateTradeComponent,
    TradeCreatedConfirmationComponent,
    TradeCreationFailureComponent
  ],
  imports: [
      BrowserModule,
      AppRoutingModule,
      FormsModule,
      HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
