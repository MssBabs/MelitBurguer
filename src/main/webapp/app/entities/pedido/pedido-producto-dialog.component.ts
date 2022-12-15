import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { NgbActiveModal, NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IProducto } from 'app/shared/model/producto.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { ITipoProducto } from 'app/shared/model/tipo-producto.model';
import { TipoProductoService } from '../tipo-producto/tipo-producto.service';
import { ProductoService } from '../producto/producto.service';

@Component({
  selector: 'jhi-pedido-producto-dialog',
  templateUrl: './pedido-producto-dialog.component.html'
})
export class PedidoProductoPopupComponent {}
