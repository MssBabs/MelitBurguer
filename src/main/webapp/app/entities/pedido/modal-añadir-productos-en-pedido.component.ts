import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IProducto } from 'app/shared/model/producto.model';

@Component({
  selector: 'ngbd-modal-basic',
  templateUrl: './modal-añadir-productos-en-pedido.component.html'
})
export class NgbdModalContent {
  @Input() producto: IProducto;

  constructor(public activeModal: NgbActiveModal) {}

  close() {
    this.activeModal.dismiss('cancel');
  }

  confirmInfo() {
    this.activeModal.close('Modal confirmado');
  }
}
