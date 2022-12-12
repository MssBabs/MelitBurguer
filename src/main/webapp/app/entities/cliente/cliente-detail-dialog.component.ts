import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from './cliente.service';

@Component({
  selector: 'ngbd-modal-content',
  templateUrl: './cliente-detail-dialog.component.html'
})
export class ClienteDetailPopupComponent {
  @Input() cliente: ICliente;

  constructor(private activeModal: NgbActiveModal, protected clienteService: ClienteService) {}
}
