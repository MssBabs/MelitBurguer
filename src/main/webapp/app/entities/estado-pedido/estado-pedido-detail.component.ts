import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstadoPedido } from 'app/shared/model/estado-pedido.model';

@Component({
  selector: 'jhi-estado-pedido-detail',
  templateUrl: './estado-pedido-detail.component.html'
})
export class EstadoPedidoDetailComponent implements OnInit {
  estadoPedido: IEstadoPedido;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estadoPedido }) => {
      this.estadoPedido = estadoPedido;
    });
  }

  previousState() {
    window.history.back();
  }
}
