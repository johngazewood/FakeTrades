import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { ViewTradesComponent } from './view-trades/view-trades.component';
import { CreateTradeComponent } from './create-trade/create-trade.component';
import { TradeCreatedConfirmationComponent } from './trade-created-confirmation/trade-created-confirmation.component';
import { TradeCreationFailureComponent } from './trade-creation-failure/trade-creation-failure.component';

const routes: Routes = [
    {path: 'viewtrades', component: ViewTradesComponent},
    {path: 'createtrade', component: CreateTradeComponent},
    {path: 'tradecreatedconfirmation/:tradeid', component: TradeCreatedConfirmationComponent},
    {path: 'tradecreationfailure', component: TradeCreationFailureComponent}
];

@NgModule({
  imports: [
      RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }

