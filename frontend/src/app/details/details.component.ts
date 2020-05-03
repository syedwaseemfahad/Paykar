import { Component, OnInit } from '@angular/core';
import { User } from '../models/user';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthenticateService } from '../authenticate.service';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {

name;
phone2;
password;
email;
  phone:string='';
  link;
  user:User;
  editopen=false;
  json={}

  constructor(private router: Router,private http:HttpClient,private validateservice: AuthenticateService) { }

  ngOnInit(): void {
    this.phone=this.validateservice.number
       this.validateservice.getUser(this.phone)
 .subscribe(response => {
  
    var obj;
    obj={
     
      name: response.name,
  mobileNumber:response.mobileNumber ,
  password:response.password,
  email: response.email,
  accountNumber: response.accountNumber,
  balance:response.balance
    }
    this.user=obj
});
  }
  edit()
  {
    this.editopen=true
    this.name=this.user.name
    this.phone2=this.user.mobileNumber
    this.password=this.user.password
    this.email=this.user.email
  }
  change()
  {
  this.editopen=false
  this.json['name']=this.name
  this.json['mobileNumber']=this.phone
  this.json['password']=this.password
  this.json['email']=this.email
  this.json['accountNumber']='001'
  this.json['balance']=0.0
  this.router.navigate(['details'])
  
  return this.validateservice.change(this.phone,this.json).subscribe();
  
  }


  delete()
  {
    this.validateservice.logout();
    this.validateservice.login=false;
    this.router.navigate(['/login'])
    return this.validateservice.delete(this.phone).subscribe();
  }
}
