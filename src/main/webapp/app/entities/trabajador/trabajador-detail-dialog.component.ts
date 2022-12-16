import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TrabajadorService } from './trabajador.service';

import { ITrabajador } from 'app/shared/model/trabajador.model';
import { TrabajadorUpdatePopupComponent } from './trabajador-update-dialog.component';

@Component({
  selector: 'ngbd-modal-content',
  templateUrl: './trabajador-detail-dialog.component.html'
})
export class TrabajadorDetailPopupComponent {
  @Input() trabajador: ITrabajador;

  constructor(private activeModal: NgbActiveModal, protected trabajadorService: TrabajadorService, private modalService: NgbModal) {}

  editModel() {
    const modalref = this.modalService.open(TrabajadorUpdatePopupComponent);
    modalref.componentInstance.trabajador = this.trabajador;
    this.activeModal.dismiss();
  }
}
