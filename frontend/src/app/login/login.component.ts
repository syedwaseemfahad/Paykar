import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticateService } from '../authenticate.service';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  phone='6050505099'
  password = 'bhanubhanu'
  validphone:boolean=false;
  validpassword:boolean=true;
  LoginInvalid = false
phoneDescription='';
passwordDescription='';
passworderror='';

  constructor(private router: Router,
    private validateservice: AuthenticateService) { }

  ngOnInit() {
    
    this.validateservice.login=true;
    
  }







Signup()
{
this.router.navigate(['/register'])
}








  handleLogin() {
    this.phoneNumberExists()
    this.phoneDescription='';
    this.passwordDescription='';
   
    if(this.phone=='')
    {
      this.phoneDescription='This field is required'
    }
    else if(this.phone.length!=10)
    {
      this.phoneDescription='Phone Number Should be 10 Digit'
    }
    else if(this.password=='')
    {
      this.passwordDescription='This field is required'
    }
    else if(this.password.length>14 || this.password.length<6)
    {
      this.passwordDescription='Password Should be 6-14 Characters'
    }
    else if(this.passwordDescription=='' && this.phoneDescription=='')
    {
      this.validpassword=false;
    this.validateservice.verify(this.phone, this.password).subscribe(response => {
      if(response)
      {
        this.validpassword=true;
        this.router.navigate(['userpage',this.phone])
        sessionStorage.setItem('authenticaterUser', this.phone);
        this.validateservice.number=this.phone;
        this.validateservice.val=true;
      }
    }
      ,
      err => {
        this.validateservice.val=false;
        this.passworderror= err.error.errorMessage;
      }
    
      // else{
      //   this.validateservice.val=false;
      // }
    )

    
    }
    
  }




  phoneNumberExists()
{
  this.validphone=<boolean><any>this.validateservice.verifyPhoneNumberLink(this.phone);
  console.log(this.validphone+"juhygfdfgvhb")
    return this.validphone;
}
}

