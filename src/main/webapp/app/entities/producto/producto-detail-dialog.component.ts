import { Component, Input } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ProductoService } from './producto.service';
import { ProductoUpdatePopupComponent } from './producto-update-dialog.component';
import { IProducto } from '../../shared/model/producto.model';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { JhiAlertService, JhiParseLinks } from 'ng-jhipster';

@Component({
  selector: 'ngbd-modal-content',
  templateUrl: './producto-detail-dialog.component.html'
})
export class ProductoDetailPopupComponent {
  @Input() producto: IProducto;

  constructor(private activeModal: NgbActiveModal, protected productoService: ProductoService, private modalService: NgbModal) {}

  editModel() {
    const modalref = this.modalService.open(ProductoUpdatePopupComponent);
    modalref.componentInstance.producto = this.producto;
    this.activeModal.dismiss();
  }
}
