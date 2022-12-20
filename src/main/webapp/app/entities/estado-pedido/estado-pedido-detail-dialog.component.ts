import { Component, Input, OnInit } from '@angular/core';

import { NgbActiveModal, NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';

import { IEstadoPedido } from 'app/shared/model/estado-pedido.model';
import { EstadoPedidoService } from './estado-pedido.service';

@Component({
  selector: 'ngbd-modal-content',
  templateUrl: './estado-pedido-detail-dialog.component.html'
})
export class EstadoPedidoDetailPopupComponent {
  @Input() estadoPedido: IEstadoPedido;

  constructor(private activeModal: NgbActiveModal, protected estadoPedidoService: EstadoPedidoService, private modalService: NgbModal) {}
}
