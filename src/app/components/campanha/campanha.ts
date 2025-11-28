import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'app-campanha',
  imports: [CommonModule],
  templateUrl: './campanha.html',
  styleUrl: './campanha.css',
})
export class Campanha {
  titulo = 'Torne-se o Noel de alguém';
  autor = 'Nicolau Batista dos Santos';
  categoria = 'Social';
  imagem = '';
  urlCampanha = 'apoia.aqui.com/34209/torne-se-o-noel-de-alguem';
  sobre = `Olá pessoal! 
Como vocês sabem, este Natal queremos levar alegria para as crianças do orfanato Ágape. 
Estamos arrecadando doações para presentes, roupas e alimentos, garantindo um Natal inesquecível para elas. Sua contribuição pode ajudar a 
tornar o natal de uma criança mágico e inesquecível!`;
  mensagens = 102;
  doadores = 256;
  valorArrecadado = 14570;
  meta = 10000;

  get percentual() {
    return Math.round((this.valorArrecadado / this.meta) * 100);
  }

  compartilhar() {
    alert('Link copiado para compartilhar: ' + this.urlCampanha);
  }

  denunciar() {
    alert('Campanha denunciada.');
  }
}
