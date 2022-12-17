import { Component, OnInit } from '@angular/core';
import {} from '@angular/router';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountService } from 'app/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IPedido } from 'app/shared/model/pedido.model';
import { IProductosPedido } from 'app/shared/model/productos-pedido.model';
import { ProductosPedidoService } from '../productos-pedido/productos-pedido.service';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from '../producto/producto.service';
import { Producto } from '../../shared/model/producto.model';
import { PedidoProductoPopupComponent } from './pedido-producto-dialog.component';

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

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected productosPedidoService: ProductosPedidoService,
    protected productoService: ProductoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected parseLinks: JhiParseLinks,
    protected accountService: AccountService,
    private modalService: NgbModal
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
  }

  protected paginateProductos(data: IProducto[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.productos = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  protected paginateProductosPedidos(data: IProductosPedido[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.productosPedidos = data;
  }
}
