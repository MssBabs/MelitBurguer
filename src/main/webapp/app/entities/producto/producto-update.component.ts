import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProducto, Producto } from 'app/shared/model/producto.model';
import { ProductoService } from './producto.service';
import { ITipoProducto } from 'app/shared/model/tipo-producto.model';
import { TipoProductoService } from 'app/entities/tipo-producto';

@Component({
  selector: 'jhi-producto-update',
  templateUrl: './producto-update.component.html'
})
export class ProductoUpdateComponent implements OnInit {
  producto: IProducto;
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
    protected tipoProductoService: TipoProductoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ producto }) => {
      this.updateForm(producto);
      this.producto = producto;
    });
    this.tipoProductoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITipoProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITipoProducto[]>) => response.body)
      )
      .subscribe((res: ITipoProducto[]) => (this.tipoproductos = res), (res: HttpErrorResponse) => this.onError(res.message));
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
    window.history.back();
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
    this.previousState();
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
