import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClienteRecompensa } from 'app/shared/model/cliente-recompensa.model';

@Component({
  selector: 'jhi-cliente-recompensa-detail',
  templateUrl: './cliente-recompensa-detail.component.html'
})
export class ClienteRecompensaDetailComponent implements OnInit {
  clienteRecompensa: IClienteRecompensa;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ clienteRecompensa }) => {
      this.clienteRecompensa = clienteRecompensa;
    });
  }

  previousState() {
    window.history.back();
  }
}
