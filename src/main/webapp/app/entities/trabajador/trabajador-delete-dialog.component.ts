import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITrabajador } from 'app/shared/model/trabajador.model';
import { TrabajadorService } from './trabajador.service';

@Component({
  selector: 'jhi-trabajador-delete-dialog',
  templateUrl: './trabajador-delete-dialog.component.html'
})
export class TrabajadorDeleteDialogComponent {
  trabajador: ITrabajador;

  constructor(
    protected trabajadorService: TrabajadorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.trabajadorService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'trabajadorListModification',
        content: 'Deleted an trabajador'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-trabajador-delete-popup',
  template: ''
})
export class TrabajadorDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ trabajador }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TrabajadorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.trabajador = trabajador;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/trabajador', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/trabajador', { outlets: { popup: null } }]);
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
