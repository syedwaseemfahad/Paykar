import { Component, OnInit } from '@angular/core';
import { Transaction } from '../models/transaction';
import { AuthenticateService } from '../authenticate.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-showtransactions',
  templateUrl: './showtransactions.component.html',
  styleUrls: ['./showtransactions.component.css']
})
export class ShowtransactionsComponent implements OnInit {
  itr=0;
  search:any=''
  phone:string='';
  link;
  trans:Transaction[]=[];
  searchtrans:Transaction[]=[];
  transobj:Transaction;
  limit1=0
  limit2=7
  slimit1=0
  slimit2=0
  var1=0
  var2=0
  var3=0
  var4=0
  var5=0
  constructor(private router: Router,private http:HttpClient,private validateservice: AuthenticateService) { }

  ngOnInit(): void {
    this.phone=this.validateservice.number
  
       this.validateservice.getTransactionByPhone(this.phone)
 .subscribe(response => {
  response.forEach((item,index)=>{
    var obj;
    obj={
      id: 'PK'+item.id.slice(2,item.id.length),
  accountNumber: item.accountNumber,
  type:item.type,
  amount: item.amount,
  time:item.time,
  balance: item.balance
    }
    this.transobj=obj
    //console.log(this.transobj)
   this.trans.push(this.transobj)
});
  });
  
  console.log(this.trans)
  }
  old()
  {
    
    this.limit1=Math.min(this.limit1+7,this.trans.length)
    this.limit2=Math.min(this.limit2+7,this.trans.length)

  }
  new()
  {
    this.limit2=Math.max(this.limit1,0)
    this.limit1=Math.max(this.limit1-7,0)
  }
  searcher()
  {
    this.searchtrans=[]
    this.itr=0
while(this.itr<this.trans.length)
{
  if(this.search.toUpperCase()==this.trans[this.itr].id.slice(0,this.search.length).toUpperCase() || this.search.toUpperCase()==this.trans[this.itr].type.slice(0,this.search.length).toUpperCase() || this.search.toUpperCase()==this.trans[this.itr].amount.toString().slice(0,this.search.length).toUpperCase() || this.search.toUpperCase()==this.trans[this.itr].time.slice(0,this.search.length).toUpperCase() || this.search.toUpperCase()==this.trans[this.itr].balance.toString().slice(0,this.search.length).toUpperCase() || this.search.toUpperCase()==this.trans[this.itr].type.slice(this.trans[this.itr].type.length-10,this.trans[this.itr].type.length-10+this.search.length).toUpperCase())
  {
    this.searchtrans.push(this.trans[this.itr])
  }
  this.itr++
}
  }
  sortById()
  {
    this.limit1=0
    this.limit2=7
    if(this.var1==0)
    {
      this.trans=sort_by_key(this.trans,'id')
      this.var1=1
    }
    else
    {
      this.trans=sort_by_key_desc(this.trans,'id')
      this.var1=0
    }
  }
  sortByDescription()
  {
    this.limit1=0
    this.limit2=7
    if(this.var2==0)
    {
      this.trans=sort_by_key(this.trans,'id')
      this.var2=1
    }
    else
    {
      this.trans=sort_by_key_desc(this.trans,'type')
      this.var2=0
    }
  }sortByAmount()
  {
    this.limit1=0
    this.limit2=7
    if(this.var3==0)
    {
      this.trans=sort_by_key(this.trans,'amount')
      this.var3=1
    }
    else
    {
      this.trans=sort_by_key_desc(this.trans,'amount')
      this.var3=0
    }
  }sortBybalance()
  {
    this.limit1=0
    this.limit2=7
    if(this.var4==0)
    {
      this.trans=sort_by_key(this.trans,'balance')
      this.var4=1
    }
    else
    {
      this.trans=sort_by_key_desc(this.trans,'balance')
      this.var4=0
    }
  }sortBydate()
  {
    this.limit1=0
    this.limit2=7
    if(this.var5==0)
    {
      this.trans=sort_by_key(this.trans,'time')
      this.var5=1
    }
    else
    {
      this.trans=sort_by_key_desc(this.trans,'time')
      this.var5=0
    }
  }
}
function sort_by_key(array, key)
{
 return array.sort(function(a, b)
 {
  var x = a[key]; var y = b[key];
  return ((x < y) ? -1 : ((x > y) ? 1 : 0));
 });
}
function sort_by_key_desc(array, key)
{
 return array.sort(function(a, b)
 {
  var x = a[key]; var y = b[key];
  return ((x < y) ? 1 : ((x > y) ? -1 : 0));
 });
}
