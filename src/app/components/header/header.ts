import { Component } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-header',
  standalone: true,
  imports: [],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {
  constructor(private router: Router) {}

  entrar() {
    this.router.navigate(['/login']);
  }
  cadastrar() {
    this.router.navigate(['/cadastro']);
  }

}


