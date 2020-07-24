import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { ViewTradesComponent } from './view-trades/view-trades.component';
import { CreateTradeComponent } from './create-trade/create-trade.component';

const routes: Routes = [
    {path: 'viewtrades', component: ViewTradesComponent},
    {path: 'createtrade', component: CreateTradeComponent}
];

@NgModule({
  imports: [
      RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
