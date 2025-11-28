import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Header } from "../header/header";

@Component({
  selector: 'app-campanha',
  imports: [CommonModule, Header],
  templateUrl: './campanha.html',
  styleUrl: './campanha.css',
})
export class Campanha {
  titulo = 'Torne-se o Noel de alguém';
  autor = 'Nicolau Batista dos Santos';
  imagem = 'https://media.gazetadopovo.com.br/2015/12/a90c1a8638cf69548f032fe7c6776316-full.jpg';
  urlCampanha = 'apoia.aqui.com/34209/torne-se-o-noel-de-alguem';
  sobre = `Olá pessoal! Como vocês sabem, este Natal queremos levar alegria para as crianças do orfanato Ágape. 
Estamos arrecadando doações para presentes, roupas e alimentos, garantindo um Natal inesquecível para elas. Sua contribuição pode ajudar a tornar o natal de uma criança mágico e inesquecível!`;
  mensagens = 102;
  doadores = 256;
  valorArrecadado = 8570;
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

  doar() {
  alert('Obrigado por contribuir com a campanha! Sua doação foi registrada.');
}

}
