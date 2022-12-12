import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IProducto } from 'app/shared/model/producto.model';
import { ITipoProducto } from 'app/shared/model/tipo-producto.model';
import { ProductoService } from '../producto';
import { TipoProductoService } from '../tipo-producto';

@Component({
  selector: 'ngbd-modal-basic',
  templateUrl: '../modals/modal-añadir-productos-en-pedido.component.html'
})
export class NgbdModalContent {
  @Input() productos: IProducto[];

  currentAccount: any;

  tipoProductos: ITipoProducto[];

  error: any;
  success: any;

  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;
  parseLinks: any;
  jhiAlertService: any;

  constructor(
    public activeModal: NgbActiveModal,
    protected productoService: ProductoService,
    protected tipoProductoService: TipoProductoService
  ) {}

  close() {
    this.activeModal.dismiss('cancel');
  }

  confirmInfo() {
    this.activeModal.close('Modal confirmado');
  }
  getProductosByName() {
    //console.log(evento);
    this.productoService
      .getProductosByName({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IProducto[]>) => this.paginateProductos(res.body, res.headers),
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

  protected paginateProductos(data: IProducto[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.productos = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackProductosByName(index: number, item: IProducto) {
    return item.nombre;
  }
}
