import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthenticateService } from '../authenticate.service';
@Component({
  selector: 'app-userpage',
  templateUrl: './userpage.component.html',
  styleUrls: ['./userpage.component.css']
})
export class UserpageComponent implements OnInit {
  phone = ''
  name:string=''
link:string;
showUser:boolean=false;
  constructor( private http:HttpClient,private route:ActivatedRoute,private validateservice:AuthenticateService) { }

  ngOnInit(): void {
    setTimeout(() => { this.showUser=true   }, 1500);

    this.validateservice.getUser(this.route.snapshot.params['phone']).subscribe(
response=>
{
this.name=response.name
}
    );
  }


}
