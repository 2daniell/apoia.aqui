import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Header } from '../header/header';

@Component({
  selector: 'app-unauthorized',
  imports: [RouterLink, Header],
  templateUrl: './unauthorized.html',
  styleUrl: './unauthorized.css',
})
export class Unauthorized {

}
