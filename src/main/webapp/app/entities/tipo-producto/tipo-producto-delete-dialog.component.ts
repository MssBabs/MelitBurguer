import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoProducto } from 'app/shared/model/tipo-producto.model';
import { TipoProductoService } from './tipo-producto.service';

@Component({
  selector: 'jhi-tipo-producto-delete-dialog',
  templateUrl: './tipo-producto-delete-dialog.component.html'
})
export class TipoProductoDeleteDialogComponent {
  tipoProducto: ITipoProducto;

  constructor(
    protected tipoProductoService: TipoProductoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tipoProductoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tipoProductoListModification',
        content: 'Deleted an tipoProducto'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-tipo-producto-delete-popup',
  template: ''
})
export class TipoProductoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipoProducto }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TipoProductoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tipoProducto = tipoProducto;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/tipo-producto', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/tipo-producto', { outlets: { popup: null } }]);
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
