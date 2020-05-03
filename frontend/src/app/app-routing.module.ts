import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { LogoutComponent } from './logout/logout.component';
import { TransactionsComponent } from './transactions/transactions.component';
import { DepositComponent } from './deposit/deposit.component';
import { WithdrawComponent } from './withdraw/withdraw.component';
import { FundTransferComponent } from './fund-transfer/fund-transfer.component';
import { UserpageComponent } from './userpage/userpage.component';
import { ShowtransactionsComponent } from './showtransactions/showtransactions.component';
import { DetailsComponent } from './details/details.component';
import { AboutComponent } from './about/about.component';
import { ContactComponent } from './contact/contact.component';


const routes: Routes = [
  { path: '', component: HomeComponent  },
  { path: 'login', component: LoginComponent },
  { path: 'about', component: AboutComponent },
  { path: 'contact', component: ContactComponent },
  { path: 'register', component: RegisterComponent},
  { path: 'userpage/:phone', component: UserpageComponent},
  { path: 'deposit', component: DepositComponent},
  { path: 'withdraw', component: WithdrawComponent},
  { path: 'fundtransfer', component: FundTransferComponent},
  { path: 'transactions', component: TransactionsComponent },
  { path: 'logout', component: LogoutComponent },
  { path: 'showtransactions', component: ShowtransactionsComponent },
  { path: 'reciept/:phone', component: TransactionsComponent },
  { path: 'details', component: DetailsComponent },
  { path: '**', component: HomeComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
