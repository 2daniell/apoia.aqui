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

  public readonly router: Router;

  constructor(router: Router) {
    this.router = router;
  }

  entrar() {
    this.router.navigate(['/login']);
  }
}


