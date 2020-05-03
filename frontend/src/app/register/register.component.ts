import { Component, OnInit } from '@angular/core';
import { AuthenticateService } from '../authenticate.service';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
// export

export class RegisterComponent implements OnInit {

message='';
json={}
newUser = "user" 
newValue = "value" 
name:string='';
  phone:string='';
  password:string='';
  email:string='';
  accountNumber:number;
  balance:number;    
password1:string='';
nameDescription='';
mobileDescription='';
emailDescription='';
passwordDescription='';
password1Description='';
errormessage='';
  constructor(private validateservice: AuthenticateService,private http:HttpClient,private router: Router,private service:AuthenticateService) { }

  ngOnInit(): void {

  }
  goToLogin()
  {
    this.router.navigate(['login'])
  }
  signed()
  {
    this.nameDescription='';
    this.mobileDescription='';
    this.emailDescription='';
    this.passwordDescription='';
    this.password1Description='';
     if(this.name=='')
    {
      this.nameDescription='This field is required';
    }
    else if(this.name.length<6)
    {
      this.nameDescription='Name should have atleast 6 characters';
    }
    else if(this.phone=='')
    {
      this.mobileDescription='This field is required';
    }
    else if(this.phone.toString().length!=10)
    {
      this.mobileDescription='Mobile Number Should be 10 Digit';
    }
    else if(!(this.phone.toString().charAt(0)>='6'))
    {
      this.mobileDescription='Mobile Number Shoulg Start With 6-9';
    }
    else if(this.email=='')
    {
      this.emailDescription='This field is required';
    }
    else if(!(this.email.indexOf('@') !== -1))
    {
      this.emailDescription='Enter valid Email';
    }
    else if(this.password=='')
    {
      this.passwordDescription='This field is required';
    }
    else if(this.password.length<6)
    {
      this.passwordDescription='Password should have atleast 6 characters';
    }
    else if(this.password.length>14)
    {
      this.passwordDescription='Password should haveless than 15 characters';
    }

    else if((this.password1!=this.password))
    {
      this.password1Description='Your Passwords Do Not Match';
    }
    else if(this.password1=='')
    {
      this.password1Description='This field is required';
    }
    
    
    
    if(   this.nameDescription=='' && 
    this.mobileDescription=='' && 
    this.emailDescription==''  &&
    this.passwordDescription=='' &&
    this.password1Description=='')
    {
  this.json['name']=this.name
  this.json['mobileNumber']=this.phone
  this.json['password']=this.password
  this.json['email']=this.email
  this.json['accountNumber']=0
  this.json['balance']=0.0
  this.validateservice.signUpVerify(this.phone,this.email).subscribe(
    response => {
      if(response)
      {
        this.router.navigate(['login'])
        return this.validateservice.SignUp(this.json)
      }
    }
      ,
      err => {
        this.errormessage= err.error.errorMessage;
      }

    )
  
    }
  }

}
