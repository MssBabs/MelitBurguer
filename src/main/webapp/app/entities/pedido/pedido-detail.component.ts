import { Component, OnInit } from '@angular/core';
import {} from '@angular/router';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountService } from 'app/core';

import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IPedido } from 'app/shared/model/pedido.model';
import { IProductosPedido } from 'app/shared/model/productos-pedido.model';
import { ProductosPedidoService } from '../productos-pedido/productos-pedido.service';

@Component({
  selector: 'jhi-pedido-detail',
  templateUrl: './pedido-detail.component.html'
})
export class PedidoDetailComponent implements OnInit {
  pedido: IPedido;
  productosPedido: IProductosPedido;
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
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected parseLinks: JhiParseLinks,
    protected accountService: AccountService
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pedido }) => {
      this.pedido = pedido;
    });
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
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
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateProductosPedidos(data: IProductosPedido[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.productosPedidos = data;
  }
}
