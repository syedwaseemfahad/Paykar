import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthenticateService } from '../authenticate.service';
import { User } from '../models/user';

@Component({
  selector: 'app-fund-transfer',
  templateUrl: './fund-transfer.component.html',
  styleUrls: ['./fund-transfer.component.css']
})
export class FundTransferComponent implements OnInit {
  notValidReceiver:boolean=true;
  amountDescription=''
  description=''
  receiverDescription=''
  Amount:number;
  phone:string='';
  rphone:string=''
  link;
  transobj: User;
  trans: User[]=[];
  limit1: number=0;
  limit2: number=0;
  searchtrans: User[]=[];
  itr: number=0;
  search: string='';
    constructor(private router: Router,private http:HttpClient,private validateservice: AuthenticateService) { }
    ngOnInit(): void {
      this.phone=this.validateservice.number
     this.link='http://localhost:8099/api/all';
      console.log(this.phone)
  
         this.validateservice.getAll()
   .subscribe(response => {
    response.forEach((item,index)=>{
      var obj;
      obj={
        accno:item.accountNumber,
      name:item.name,
    mobileNumber:item.mobileNumber,
    pass:item.password,
    email:item.email,
    bal:item.balance
      }
      this.transobj=obj
      console.log(this.transobj)
     this.trans.push(this.transobj)
  });
    });
    
    console.log(this.trans)
    }
   




    searcher()
    {
      this.search=this.rphone
      this.searchtrans=[]
      this.itr=0
  while(this.itr<this.trans.length)
  {
    if(this.search.toUpperCase()==this.trans[this.itr].name.slice(0,this.search.length).toUpperCase() || this.search.toUpperCase()==this.trans[this.itr].mobileNumber.slice(0,this.search.length).toUpperCase())
    {
      if(this.trans[this.itr].mobileNumber!=this.phone)
      {
        this.searchtrans.push(this.trans[this.itr])
      }
    }
    this.itr++
  }
console.log(this.searchtrans.length)
    }
    selectNumber(mobile:any)
    {
      this.rphone=mobile
    }






    fundtransfer()
    {
      this.notValidReceiver=true
      this.description='';
      this.receiverDescription='';
      this.amountDescription='';
      this.trans.forEach((item,index)=>{
        if(item.mobileNumber==this.rphone)
        {
          this.notValidReceiver=false
        }
      })
      if(this.Amount<100 && this.Amount>=0)
      {
        this.amountDescription='Minimum Amount to be sent is 100';
      }
      else if(this.Amount<0 )
      {
        this.amountDescription='Enter a Valid Amount';
      }
      else if(this.Amount==null){
        this.amountDescription='Please Enter Amount';
      }
      else if(this.rphone=='')
      {
this.receiverDescription='Select Money Receiver'
      }
      else if(this.rphone.length!=10)
      {
this.receiverDescription='Enter Valid Mobile Number'
      }
      else if(this.notValidReceiver)
      {
        this.receiverDescription='Receiver Not Registered'
      }
      else{
      this.phone=this.validateservice.number
      //return this.http.get("http://localhost:8099/api/all/deposit/" +this.validateservice.number+ "/" +this.amount).subscribe();
     
     this.validateservice.fundTransfer(this.phone,this.rphone,this.Amount).subscribe(response => {
        if(response)
        {
          setTimeout(() => {  this.router.navigate(['reciept',this.phone]) }, 1200);
        }
      }
        ,
        err => {
          this.description=err.error.errorMessage;
              }
       );
    
    }
  }


    changeAmount200()
    {
      this.Amount=200;
    }



    changeAmount500()
    {
      this.Amount=500;
    }
    
    
    changeAmount1000()
    {
      this.Amount=1000;
    }
}
