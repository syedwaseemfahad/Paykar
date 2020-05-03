import { Component, OnInit } from '@angular/core';
import { AuthenticateService } from '../authenticate.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(private service:AuthenticateService,private router: Router) { }

  ngOnInit(): void {

    this.service.logout();
    this.service.login=false;
    this.router.navigate(['/login'])
  }

}
