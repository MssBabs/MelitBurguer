import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductosPedido, ProductosPedido } from 'app/shared/model/productos-pedido.model';
import { ProductosPedidoService } from './productos-pedido.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';
import { IPedido } from 'app/shared/model/pedido.model';
import { PedidoService } from 'app/entities/pedido';

@Component({
  selector: 'jhi-productos-pedido-update',
  templateUrl: './productos-pedido-update.component.html'
})
export class ProductosPedidoUpdateComponent implements OnInit {
  productosPedido: IProductosPedido;
  isSaving: boolean;

  productos: IProducto[];

  pedidos: IPedido[];

  editForm = this.fb.group({
    id: [],
    precioTotal: [],
    productosId: [],
    pedidoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productosPedidoService: ProductosPedidoService,
    protected productoService: ProductoService,
    protected pedidoService: PedidoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productosPedido }) => {
      this.updateForm(productosPedido);
      this.productosPedido = productosPedido;
    });
    this.productoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProducto[]>) => response.body)
      )
      .subscribe((res: IProducto[]) => (this.productos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.pedidoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPedido[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPedido[]>) => response.body)
      )
      .subscribe((res: IPedido[]) => (this.pedidos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(productosPedido: IProductosPedido) {
    this.editForm.patchValue({
      id: productosPedido.id,
      precioTotal: productosPedido.precioTotal,
      productosId: productosPedido.productosId,
      pedidoId: productosPedido.pedidoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productosPedido = this.createFromForm();
    if (productosPedido.id !== undefined) {
      this.subscribeToSaveResponse(this.productosPedidoService.update(productosPedido));
    } else {
      this.subscribeToSaveResponse(this.productosPedidoService.create(productosPedido));
    }
  }

  private createFromForm(): IProductosPedido {
    const entity = {
      ...new ProductosPedido(),
      id: this.editForm.get(['id']).value,
      precioTotal: this.editForm.get(['precioTotal']).value,
      productosId: this.editForm.get(['productosId']).value,
      pedidoId: this.editForm.get(['pedidoId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductosPedido>>) {
    result.subscribe((res: HttpResponse<IProductosPedido>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackProductoById(index: number, item: IProducto) {
    return item.id;
  }

  trackPedidoById(index: number, item: IPedido) {
    return item.id;
  }
}
