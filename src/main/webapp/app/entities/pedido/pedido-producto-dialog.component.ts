import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { NgbActiveModal, NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormArray, Validators, FormControl } from '@angular/forms';
import { Observable, Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IProducto } from 'app/shared/model/producto.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { ITipoProducto } from 'app/shared/model/tipo-producto.model';
import { TipoProductoService } from '../tipo-producto/tipo-producto.service';
import { ProductoService } from '../producto/producto.service';
import { IPedido } from 'app/shared/model/pedido.model';
import { IProductosPedido, ProductosPedido } from 'app/shared/model/productos-pedido.model';
import { ProductosPedidoService } from '../productos-pedido/productos-pedido.service';
import { PedidoProductoDeletePopupComponent } from './pedido-producto-delete-dialog.component';

@Component({
  selector: 'jhi-pedido-producto-dialog',
  templateUrl: './pedido-producto-dialog.component.html'
})
export class PedidoProductoPopupComponent {
  productos: IProducto[];
  pedidoId: any;
  productoPedido = this.fb.array([]);
  form = this.fb.group({
    productoPedido: this.productoPedido
  });

  ngOnInit() {
    for (var producto of this.productos) {
      this.addProductoPedido(producto);
    }
  }

  constructor(
    private activeModal: NgbActiveModal,
    protected productoService: ProductoService,
    private modalService: NgbModal,
    private fb: FormBuilder,
    protected activatedRoute: ActivatedRoute,
    protected productosPedidoService: ProductosPedidoService
  ) {}

  setNumber(number: any) {
    let objeto = this.productoPedido.controls[number].value;
    objeto.numero++;
    //el patch value permite modificar el campo
    this.productoPedido.controls[number].patchValue(objeto);
    console.log(this.productoPedido.controls[number]);
  }
  delNumber(number: any) {
    let objeto = this.productoPedido.controls[number].value;
    objeto.numero--;
    //el patch value permite modificar el campo
    this.productoPedido.controls[number].patchValue(objeto);
  }

  addProductoPedido(producto: IProducto) {
    const productoPedido = this.fb.group({
      pedidoId: this.fb.control(this.pedidoId),
      productoId: this.fb.control(producto.id),
      productoNombre: this.fb.control(producto.nombre),
      numero: this.fb.control(0)
    });
    this.productoPedido.push(productoPedido);
  }

  getProductoNombre(i) {
    return this.productoPedido.controls[i].value.productoNombre;
  }

  save() {
    console.log('eeey');
    for (var producto of this.productoPedido.controls) {
      if (producto.value.numero > 0) {
        console.log(producto);
        const productosPedido = this.createFromForm(producto);
        this.subscribeToSaveResponse(this.productosPedidoService.create(productosPedido));
      }
    }
  }
  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductosPedido>>) {
    result.subscribe((res: HttpResponse<IProductosPedido>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }
  protected onSaveSuccess() {
    this.previousState();
  }
  previousState() {}

  protected onSaveError() {}

  private createFromForm(control): IProductosPedido {
    const entity = {
      ...new ProductosPedido(),
      id: null,
      precioTotal: control.value.numero,
      productosId: control.value.productoId,
      pedidoId: control.value.pedidoId
    };
    return entity;
  }
}
