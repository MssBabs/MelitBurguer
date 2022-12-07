import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstadoPedido } from 'app/shared/model/estado-pedido.model';
import { EstadoPedidoService } from './estado-pedido.service';

@Component({
  selector: 'jhi-estado-pedido-delete-dialog',
  templateUrl: './estado-pedido-delete-dialog.component.html'
})
export class EstadoPedidoDeleteDialogComponent {
  estadoPedido: IEstadoPedido;

  constructor(
    protected estadoPedidoService: EstadoPedidoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.estadoPedidoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'estadoPedidoListModification',
        content: 'Deleted an estadoPedido'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-estado-pedido-delete-popup',
  template: ''
})
export class EstadoPedidoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estadoPedido }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EstadoPedidoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.estadoPedido = estadoPedido;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/estado-pedido', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/estado-pedido', { outlets: { popup: null } }]);
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
