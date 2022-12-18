import { NgbActiveModal, NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { PedidoService } from 'app/entities/pedido';
import { ProductoService } from './producto.service';
import { IProducto, Producto } from '../../shared/model/producto.model';
import { ITipoProducto } from 'app/shared/model/tipo-producto.model';

@Component({
  selector: 'jhi-producto-update-dialog',
  templateUrl: './producto-update-dialog.component.html'
})
export class ProductoUpdatePopupComponent implements OnInit {
  @Input() producto: IProducto;
  isSaving: boolean;

  tipoproductos: ITipoProducto[];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    descripcion: [],
    precio: [],
    tipoProductoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productoService: ProductoService,
    protected pedidoService: PedidoService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    private fb: FormBuilder,
    public activeModal: NgbActiveModal
  ) {}

  ngOnInit() {
    this.updateForm(this.producto);
  }

  updateForm(producto: IProducto) {
    this.editForm.patchValue({
      id: producto.id,
      nombre: producto.nombre,
      descripcion: producto.descripcion,
      precio: producto.precio,
      tipoProductoId: producto.tipoProductoId
    });
  }

  previousState() {
    this.router.navigate(['/producto']);

    this.activeModal.close();
  }

  save() {
    this.isSaving = true;
    const producto = this.createFromForm();
    if (producto.id !== undefined) {
      this.subscribeToSaveResponse(this.productoService.update(producto));
    } else {
      this.subscribeToSaveResponse(this.productoService.create(producto));
    }
  }

  private createFromForm(): IProducto {
    const entity = {
      ...new Producto(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      precio: this.editForm.get(['precio']).value,
      tipoProductoId: this.editForm.get(['tipoProductoId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProducto>>) {
    result.subscribe((res: HttpResponse<IProducto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    // this.previousState();
    this.activeModal.close();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackTipoProductoById(index: number, item: ITipoProducto) {
    return item.id;
  }
}
