import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductosPedido } from 'app/shared/model/productos-pedido.model';

@Component({
  selector: 'jhi-productos-pedido-detail',
  templateUrl: './productos-pedido-detail.component.html'
})
export class ProductosPedidoDetailComponent implements OnInit {
  productosPedido: IProductosPedido;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productosPedido }) => {
      this.productosPedido = productosPedido;
    });
  }

  previousState() {
    window.history.back();
  }
}
