import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthenticateService } from './authenticate.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'paykar';
  constructor(private router: Router,private http:HttpClient,private validateservice: AuthenticateService) { }
    ngOnInit(): void {
      this.router.navigate(["/home"])
    }
}
