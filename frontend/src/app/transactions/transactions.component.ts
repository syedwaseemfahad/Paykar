import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthenticateService } from '../authenticate.service';
import { Transaction } from '../models/transaction';

@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.css']
})
export class TransactionsComponent implements OnInit {
phone='';
balance:number=0;
 link=''
 reciept;
 itr=1000000000;
    constructor(private router: ActivatedRoute,private http:HttpClient,private validateservice: AuthenticateService) { }
    ngOnInit(): void {
      
      this.phone = this.router.snapshot.params['phone'];
      setTimeout(() => {  this.itr=0 }, 3000);
  
         this.validateservice.getTransactionByPhone(this.phone)
   .subscribe(response => {
      var obj;
      obj={
        id:response[0].id,
    accountNumber: response[0].accountNumber,
    type:response[0].type,
    amount:response[0].amount,
    time:response[0].time,
    balance: response[0].balance
      }
this.reciept=obj
    });
    }
    
    

}
