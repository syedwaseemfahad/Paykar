import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { User } from './models/user';
import { Transaction } from './models/transaction';

@Injectable({
  providedIn: 'root'
})
export class AuthenticateService {
  link:String;
 login:boolean=false;
 val:boolean=false;
 number:string;
 name:string='';
   constructor(private http:HttpClient,private router: Router) { }



   getUser(phone)
  {
   return  this.http.get<User>('http://localhost:8099/api/all/' +phone)
  }





  change(phone,json)
  {
    return this.http.put("http://localhost:8099/api/all/"+phone,json)
  }





  delete(phone)
  {
    return this.http.delete("http://localhost:8099/api/all/delete/"+phone)
  }





  deposit(phone,Amount)
  {
    return this.http.get('http://localhost:8099/api/all/deposit/' +phone+ '/' +Amount);
  }





withdraw(phone,Amount)
{
  return this.http.get('http://localhost:8099/api/all/withdraw/' +phone+ '/' +Amount);
}






  fundTransfer(phone,receiverPhone,amount)
  {
    return this.http.get('http://localhost:8099/api/all/ft/' +phone+ '/' +receiverPhone+ '/' +amount)
  }




  getBalance(phone)
  {
    return this.http.get<number>('http://localhost:8099/api/all/'+phone+'/bal')
  }





  getTransactionByPhone(phone)
  {
return this.http.get<Transaction[]>('http://localhost:8099/api/all/transactions/' +phone)
  }





  inlogin()
  {
this.login=true;
  }






  getName(phone)
  {
    this.link='http://localhost:8099/api/all/'+phone+'/name';
    return this.http.get<User>(this.link+'');
  }






  verify(phone,password)
  { 
    this.link='http://localhost:8099/api/all/pass/'+phone+'/'+password;
   return this.http.get(this.link+'')

  }





  isUserLoggedIn() {
    let user = sessionStorage.getItem('authenticaterUser')
    return !(user === null)
  }





  SignUp(json)
  {
    return this.http.post("http://localhost:8099/api/all/add",json).subscribe();
  }





  logout(){
    sessionStorage.removeItem('authenticaterUser')
  }






  verifyPhoneNumberLink(phoneNumber)
  {
   return this.http.get<boolean>('http://localhost:8099/api/all/mobileNumberExists/'+phoneNumber).subscribe(response => {
    if(response)
    {
    }
    else{
    }
  });
  }



signUpVerify(phone,email)
{
  return this.http.get('http://localhost:8099/api/all/SignUp/'+phone+'/'+email)
}



getAll()
{
  return this.http.get<User[]>('http://localhost:8099/api/all')
}
  
}

