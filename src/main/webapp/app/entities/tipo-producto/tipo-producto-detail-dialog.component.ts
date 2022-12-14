import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoProducto } from 'app/shared/model/tipo-producto.model';

@Component({
  selector: 'jhi-tipo-producto-detail-dialog',
  templateUrl: './tipo-producto-detail-dialog.component.html'
})
export class TipoProductoDetailPopupComponent {
  @Input() tipoProducto: ITipoProducto;

  constructor(private activeModal: NgbActiveModal, protected activatedRoute: ActivatedRoute, private modalService: NgbModal) {}

  editModel() {
    const modalref = this.modalService.open(TipoProductoDetailPopupComponent);
    modalref.componentInstance.tipoProducto = this.tipoProducto;
    this.activeModal.dismiss();
  }
}
