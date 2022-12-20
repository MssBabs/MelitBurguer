import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IEstadoPedido } from 'app/shared/model/estado-pedido.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EstadoPedidoService } from './estado-pedido.service';
import { EstadoPedido } from '../../shared/model/estado-pedido.model';
import { EstadoPedidoDetailPopupComponent } from './estado-pedido-detail-dialog.component';
import { EstadoPedidoUpdateDialogPopupComponent } from './estado-pedido-update-dialog.component';

@Component({
  selector: 'jhi-estado-pedido',
  templateUrl: './estado-pedido.component.html'
})
export class EstadoPedidoComponent implements OnInit, OnDestroy {
  currentAccount: any;
  estadoPedidos: IEstadoPedido[];
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
    protected estadoPedidoService: EstadoPedidoService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    private modalService: NgbModal
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    this.estadoPedidoService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IEstadoPedido[]>) => this.paginateEstadoPedidos(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/estado-pedido'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/estado-pedido',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInEstadoPedidos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEstadoPedido) {
    return item.id;
  }

  registerChangeInEstadoPedidos() {
    this.eventSubscriber = this.eventManager.subscribe('estadoPedidoListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  viewModel(estadoPedidos: EstadoPedido) {
    const modalref = this.modalService.open(EstadoPedidoDetailPopupComponent);
    modalref.componentInstance.estadoPedido = estadoPedidos;
    //console.log(cliente);
  }

  editModel(estadoPedidos: EstadoPedido) {
    const modalref = this.modalService.open(EstadoPedidoUpdateDialogPopupComponent);
    modalref.componentInstance.estadoPedido = estadoPedidos;
    modalref.result.then(result => {
      this.loadAll();
    });
  }

  protected paginateEstadoPedidos(data: IEstadoPedido[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.estadoPedidos = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
