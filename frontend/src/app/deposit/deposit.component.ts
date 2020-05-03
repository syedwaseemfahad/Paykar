import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthenticateService } from '../authenticate.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-deposit',
  templateUrl: './deposit.component.html',
  styleUrls: ['./deposit.component.css']
})
export class DepositComponent implements OnInit {
Amount:number=100;
phone:string='';
link;
name;
amountDescription='';
var:boolean=true;
  constructor(private router: Router,private http:HttpClient,private validateservice: AuthenticateService) { }
  ngOnInit(): void {
  }
  deposit()
  {
    this.amountDescription='';
    if(this.Amount<100 && this.Amount>=0)
    {
      this.amountDescription='Minimum Deposit Amount is 100';
    }
    else if(this.Amount<0)
    {
      this.amountDescription='Enter a Valid Amount';
    }
    else{
    this.phone=this.validateservice.number
    console.log(this.phone)
    console.log(this.Amount)     

       this.validateservice.deposit(this.phone,this.Amount)
 .subscribe(response => {
 response
  });
  setTimeout(() => {  this.router.navigate(['reciept',this.phone]) }, 1200);
 

  }
}
  changeAmount200()
  {
    this.Amount=200;
  }
  changeAmount500()
  {
    this.Amount=500;
  }changeAmount1000()
  {
    this.Amount=1000;
  }
}
