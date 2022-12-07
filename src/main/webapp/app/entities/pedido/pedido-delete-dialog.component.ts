import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPedido } from 'app/shared/model/pedido.model';
import { PedidoService } from './pedido.service';

@Component({
  selector: 'jhi-pedido-delete-dialog',
  templateUrl: './pedido-delete-dialog.component.html'
})
export class PedidoDeleteDialogComponent {
  pedido: IPedido;

  constructor(protected pedidoService: PedidoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pedidoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pedidoListModification',
        content: 'Deleted an pedido'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pedido-delete-popup',
  template: ''
})
export class PedidoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pedido }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PedidoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pedido = pedido;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pedido', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pedido', { outlets: { popup: null } }]);
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
