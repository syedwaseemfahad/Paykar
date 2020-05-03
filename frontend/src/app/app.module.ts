import * as platformBrowser from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenubarComponent } from './menubar/menubar.component';
import { FooterComponent } from './footer/footer.component';
import { LoginComponent } from './login/login.component';
import { DepositComponent } from './deposit/deposit.component';
import { WithdrawComponent } from './withdraw/withdraw.component';
import { FundTransferComponent } from './fund-transfer/fund-transfer.component';
import { TransactionsComponent } from './transactions/transactions.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { LogoutComponent } from './logout/logout.component';
import { UserpageComponent } from './userpage/userpage.component';
import { HttpClientModule } from '@angular/common/http';
import { ShowtransactionsComponent } from './showtransactions/showtransactions.component';
import { DetailsComponent } from './details/details.component';
import { AboutComponent } from './about/about.component';
import { ContactComponent } from './contact/contact.component';

@NgModule({
  declarations: [
    AppComponent,
    MenubarComponent,
    FooterComponent,
    LoginComponent,
    DepositComponent,
    WithdrawComponent,
    FundTransferComponent,
    TransactionsComponent,
    RegisterComponent,
    HomeComponent,
    LogoutComponent,
    UserpageComponent,
    ShowtransactionsComponent,
    DetailsComponent,
    AboutComponent,
    ContactComponent,
  ],
  imports: [
    platformBrowser.BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
