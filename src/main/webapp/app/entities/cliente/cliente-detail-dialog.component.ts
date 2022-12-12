import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Cliente, ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from './cliente.service';
import { ClienteUpdatePopupComponent } from './cliente-update-dialog.component';

@Component({
  selector: 'ngbd-modal-content',
  templateUrl: './cliente-detail-dialog.component.html'
})
export class ClienteDetailPopupComponent {
  @Input() cliente: ICliente;

  constructor(private activeModal: NgbActiveModal, protected clienteService: ClienteService, private modalService: NgbModal) {}

  editModel() {
    const modalref = this.modalService.open(ClienteUpdatePopupComponent);
    modalref.componentInstance.cliente = this.cliente;
    this.activeModal.dismiss();
  }
}
