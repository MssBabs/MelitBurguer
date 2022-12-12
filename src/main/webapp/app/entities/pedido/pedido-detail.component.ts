import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPedido } from 'app/shared/model/pedido.model';
import { Producto } from 'app/shared/model/producto.model';
import { NgbdModalContent } from '../modals/modal-añadir-productos-en-pedido.component';

@Component({
  selector: 'jhi-pedido-detail',
  templateUrl: './pedido-detail.component.html'
})
export class PedidoDetailComponent implements OnInit {
  pedido: IPedido;

  constructor(protected activatedRoute: ActivatedRoute, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pedido }) => {
      this.pedido = pedido;
    });
  }

  previousState() {
    window.history.back();
  }

  open(producto: Producto) {
    const modalProducto = this.modalService.open(NgbdModalContent);

    modalProducto.componentInstance.producto = producto;
    modalProducto.result.then(result => {
      alert(result);
    });
  }
}
