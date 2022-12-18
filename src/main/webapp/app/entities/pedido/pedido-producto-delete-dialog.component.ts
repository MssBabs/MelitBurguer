import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { ProductosPedidoService } from '../productos-pedido/productos-pedido.service';

@Component({
  selector: 'jhi-pedido-producto-delete-dialog',
  templateUrl: './pedido-producto-delete-dialog.component.html'
})
export class PedidoProductoDeletePopupComponent {
  idProductoPedido: any;

  constructor(
    protected productosPedidoService: ProductosPedidoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  confirmDelete() {
    this.productosPedidoService.delete(this.idProductoPedido).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productosPedidoListModification',
        content: 'Deleted an productosPedido'
      });
      this.activeModal.dismiss(true);
    });
  }
}
