import { Component, OnInit } from '@angular/core';
import {} from '@angular/router';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountService } from 'app/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';

import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IPedido, Pedido } from 'app/shared/model/pedido.model';
import { IProductosPedido } from 'app/shared/model/productos-pedido.model';
import { ProductosPedidoService } from '../productos-pedido/productos-pedido.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from '../producto/producto.service';
import { Producto } from '../../shared/model/producto.model';
import { PedidoProductoPopupComponent } from './pedido-producto-dialog.component';
import { PedidoProductoDeletePopupComponent } from './pedido-producto-delete-dialog.component';
import { EstadoPedidoService } from '../estado-pedido/estado-pedido.service';
import { IEstadoPedido } from 'app/shared/model/estado-pedido.model';
import { PedidoService } from './pedido.service';
import { element } from '@angular/core/src/render3';

@Component({
  selector: 'jhi-pedido-detail',
  templateUrl: './pedido-detail.component.html'
})
export class PedidoDetailComponent implements OnInit {
  pedido: IPedido;
  productosPedido: IProductosPedido;
  productos: IProducto[];
  page: any;
  itemsPerPage: any;
  predicate: any;
  reverse: any;
  eventSubscriber: Subscription;
  links: any;
  totalItems: any;
  productosPedidos: IProductosPedido[];
  currentAccount: any;
  estadoPedidos: IEstadoPedido[];
  isSaving: boolean;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected productosPedidoService: ProductosPedidoService,
    protected productoService: ProductoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected parseLinks: JhiParseLinks,
    protected accountService: AccountService,
    private modalService: NgbModal,
    protected estadoPedidoService: EstadoPedidoService,
    protected pedidoService: PedidoService
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pedido }) => {
      this.pedido = pedido;
    });
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    console.log(this.productos);
  }

  previousState() {
    window.history.back();
  }

  loadAll() {
    this.productosPedidoService
      .query({
        pedidoId: this.pedido.id
      })
      .subscribe(
        (res: HttpResponse<IProductosPedido[]>) => this.paginateProductosPedidos(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    this.productoService
      .query({})
      .subscribe(
        (res: HttpResponse<IProducto[]>) => this.paginateProductos(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    //console.log(this.productoService);
    this.estadoPedidoService
      .query({})
      .subscribe(
        (res: HttpResponse<IEstadoPedido[]>) => this.paginateEstadoPedidos(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  AddProductoModel(productos: IProducto[]) {
    const modalref = this.modalService.open(PedidoProductoPopupComponent);
    modalref.componentInstance.productos = productos;
    modalref.componentInstance.pedidoId = this.pedido.id;
    //console.log(productos);
    modalref.result.then(result => {
      this.loadAll();
    });
  }

  deleteProductoModel(id) {
    const modalref = this.modalService.open(PedidoProductoDeletePopupComponent);
    modalref.componentInstance.idProductoPedido = id;
    //console.log(ProductosPedido);
    modalref.result.then(result => {
      this.loadAll();
    });
  }

  establecerEstadoPedido(event) {
    const pedido = this.createFromForm(event);
    this.subscribeToSaveResponse(this.pedidoService.update(pedido));
    if (event == 3) {
      const button = document.querySelectorAll('.btn-delete-add');
      console.log(button);
      button.forEach(element => {
        element.setAttribute('disabled', 'true');
      });
      document.getElementById('field_estadoPedido').setAttribute('disabled', 'true');
    }
  }
  private createFromForm(event): IPedido {
    const entity = {
      ...new Pedido(),
      id: this.pedido.id,
      precioFinal: this.pedido.precioFinal,
      fecha: this.pedido.fecha,
      estadoPedidoId: event,
      clienteId: this.pedido.clienteId,
      trabajadorId: this.pedido.trabajadorId
    };
    return entity;
  }

  protected paginateProductos(data: IProducto[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.productos = data;
  }

  protected paginateEstadoPedidos(data: IEstadoPedido[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.estadoPedidos = data;
    console.log(this.estadoPedidos);
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  protected paginateProductosPedidos(data: IProductosPedido[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.productosPedidos = data;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPedido>>) {
    result.subscribe((res: HttpResponse<IPedido>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }
  protected onSaveSuccess() {
    this.isSaving = false;
    //this.previousState();
  }
  protected onSaveError() {
    this.isSaving = false;
  }
}
