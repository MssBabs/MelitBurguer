import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AccountService } from 'app/core';

import { IPedido } from 'app/shared/model/pedido.model';
import { IProducto, Producto } from 'app/shared/model/producto.model';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { Subscription } from 'rxjs';
import { ProductoService } from '../producto';
import { PedidoService } from './pedido.service';

@Component({
  selector: 'jhi-pedido-detail',
  templateUrl: './pedido-detail.component.html'
})
export class PedidoDetailComponent implements OnInit {
  pedido: IPedido;
  productos: IProducto[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  constructor(
    protected productoService: ProductoService,
    protected activatedRoute: ActivatedRoute,
    protected modalService: NgbModal,
    protected pedidoService: PedidoService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {}

  loadAll() {
    this.productoService
      .query({})
      .subscribe(
        (res: HttpResponse<IProducto[]>) => this.paginateProductos(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pedido }) => {
      this.pedido = pedido;
    });
    // this.loadAll();
  }

  previousState() {
    window.history.back();
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateProductos(data: IProducto[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.productos = data;
    // console.log(data);
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
