import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClienteRecompensa } from 'app/shared/model/cliente-recompensa.model';
import { ClienteRecompensaService } from './cliente-recompensa.service';

@Component({
  selector: 'jhi-cliente-recompensa-delete-dialog',
  templateUrl: './cliente-recompensa-delete-dialog.component.html'
})
export class ClienteRecompensaDeleteDialogComponent {
  clienteRecompensa: IClienteRecompensa;

  constructor(
    protected clienteRecompensaService: ClienteRecompensaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.clienteRecompensaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'clienteRecompensaListModification',
        content: 'Deleted an clienteRecompensa'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-cliente-recompensa-delete-popup',
  template: ''
})
export class ClienteRecompensaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ clienteRecompensa }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ClienteRecompensaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.clienteRecompensa = clienteRecompensa;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/cliente-recompensa', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/cliente-recompensa', { outlets: { popup: null } }]);
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
