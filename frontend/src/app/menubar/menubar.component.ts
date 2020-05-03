import { Component, OnInit } from '@angular/core';
import { AuthenticateService } from '../authenticate.service';

@Component({
  selector: 'app-menubar',
  templateUrl: './menubar.component.html',
  styleUrls: ['./menubar.component.css']
})
export class MenubarComponent implements OnInit {
  //isUserLoggedIn: boolean = false;
  constructor(public service:AuthenticateService) { }

  ngOnInit(): void {
    //this.isUserLoggedIn = this.service.isUserLoggedIn();
  }
   
}
