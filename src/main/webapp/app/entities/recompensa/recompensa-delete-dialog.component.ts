import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecompensa } from 'app/shared/model/recompensa.model';
import { RecompensaService } from './recompensa.service';

@Component({
  selector: 'jhi-recompensa-delete-dialog',
  templateUrl: './recompensa-delete-dialog.component.html'
})
export class RecompensaDeleteDialogComponent {
  recompensa: IRecompensa;

  constructor(
    protected recompensaService: RecompensaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.recompensaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'recompensaListModification',
        content: 'Deleted an recompensa'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-recompensa-delete-popup',
  template: ''
})
export class RecompensaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ recompensa }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RecompensaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.recompensa = recompensa;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/recompensa', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/recompensa', { outlets: { popup: null } }]);
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
