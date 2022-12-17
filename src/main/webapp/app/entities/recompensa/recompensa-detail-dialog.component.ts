import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { IRecompensa } from 'app/shared/model/recompensa.model';
import { RecompensaUpdatePopupComponent } from './recompensa-update-dialog.component';
import { RecompensaService } from './recompensa.service';

@Component({
  selector: 'ngbd-modal-content',
  templateUrl: './recompensa-detail-dialog.component.html'
})
export class RecompensaDetailPopupComponent {
  @Input() recompensa: IRecompensa;

  constructor(private activeModal: NgbActiveModal, protected recompensaService: RecompensaService, private modalService: NgbModal) {}

  editModel() {
    const modalref = this.modalService.open(RecompensaUpdatePopupComponent);
    modalref.componentInstance.recompensa = this.recompensa;
    this.activeModal.dismiss();
  }
}
