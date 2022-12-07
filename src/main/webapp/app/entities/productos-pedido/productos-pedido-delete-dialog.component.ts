import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductosPedido } from 'app/shared/model/productos-pedido.model';
import { ProductosPedidoService } from './productos-pedido.service';

@Component({
  selector: 'jhi-productos-pedido-delete-dialog',
  templateUrl: './productos-pedido-delete-dialog.component.html'
})
export class ProductosPedidoDeleteDialogComponent {
  productosPedido: IProductosPedido;

  constructor(
    protected productosPedidoService: ProductosPedidoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productosPedidoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productosPedidoListModification',
        content: 'Deleted an productosPedido'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-productos-pedido-delete-popup',
  template: ''
})
export class ProductosPedidoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productosPedido }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductosPedidoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.productosPedido = productosPedido;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/productos-pedido', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/productos-pedido', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
