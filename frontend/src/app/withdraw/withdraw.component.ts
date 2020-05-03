import { Component, OnInit } from '@angular/core';
import { AuthenticateService } from '../authenticate.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-withdraw',
  templateUrl: './withdraw.component.html',
  styleUrls: ['./withdraw.component.css']
})
export class WithdrawComponent implements OnInit {
  Amount:number=100;
  phone:string='';
  amountDescription='';
  link;
  description=''
  bal:number;
  
    constructor(private router: Router,private http:HttpClient,private validateservice: AuthenticateService) { }
    ngOnInit(): void {
    }



    withdraw()
    {
      this.description=''
      this.amountDescription='';
    if(this.Amount<100 && this.Amount>=0)
    {
      this.amountDescription='Minimum With Draw Amount is 100';
    }
    else if(this.Amount<0)
    {
      this.amountDescription='Enter a Valid Amount';
    }
    else{
      this.phone=this.validateservice.number
      //return this.http.get("http://localhost:8099/api/all/deposit/" +this.validateservice.number+ "/" +this.amount).subscribe();
      console.log(this.phone)
      console.log(this.Amount)
  
         this.validateservice.withdraw(this.phone,this.Amount).subscribe(
    response => {
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
    }changeAmount1000()
    {
      this.Amount=1000;
    }

}
