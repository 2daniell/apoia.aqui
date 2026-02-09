import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth/auth-service';


@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {

  private auth = inject(AuthService);
  
  public router = inject(Router);

  public isAuthenticated = this.auth.isAuthenticated;

  public logout() {
    this.auth.logout().subscribe({ next: () => this.router.navigate(["/"])})
  }
}


