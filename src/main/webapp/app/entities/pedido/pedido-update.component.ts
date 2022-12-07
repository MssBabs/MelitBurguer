import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPedido, Pedido } from 'app/shared/model/pedido.model';
import { PedidoService } from './pedido.service';
import { IEstadoPedido } from 'app/shared/model/estado-pedido.model';
import { EstadoPedidoService } from 'app/entities/estado-pedido';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';
import { ITrabajador } from 'app/shared/model/trabajador.model';
import { TrabajadorService } from 'app/entities/trabajador';

@Component({
  selector: 'jhi-pedido-update',
  templateUrl: './pedido-update.component.html'
})
export class PedidoUpdateComponent implements OnInit {
  pedido: IPedido;
  isSaving: boolean;

  estadopedidos: IEstadoPedido[];

  clientes: ICliente[];

  trabajadors: ITrabajador[];
  fechaDp: any;

  editForm = this.fb.group({
    id: [],
    precioFinal: [],
    fecha: [],
    estadoPedidoId: [],
    clienteId: [],
    trabajadorId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected pedidoService: PedidoService,
    protected estadoPedidoService: EstadoPedidoService,
    protected clienteService: ClienteService,
    protected trabajadorService: TrabajadorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pedido }) => {
      this.updateForm(pedido);
      this.pedido = pedido;
    });
    this.estadoPedidoService
      .query({ filter: 'pedido-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IEstadoPedido[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstadoPedido[]>) => response.body)
      )
      .subscribe(
        (res: IEstadoPedido[]) => {
          if (!this.pedido.estadoPedidoId) {
            this.estadopedidos = res;
          } else {
            this.estadoPedidoService
              .find(this.pedido.estadoPedidoId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IEstadoPedido>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IEstadoPedido>) => subResponse.body)
              )
              .subscribe(
                (subRes: IEstadoPedido) => (this.estadopedidos = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.clienteService
      .query({ filter: 'pedido-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ICliente[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICliente[]>) => response.body)
      )
      .subscribe(
        (res: ICliente[]) => {
          if (!this.pedido.clienteId) {
            this.clientes = res;
          } else {
            this.clienteService
              .find(this.pedido.clienteId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ICliente>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ICliente>) => subResponse.body)
              )
              .subscribe(
                (subRes: ICliente) => (this.clientes = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.trabajadorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITrabajador[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITrabajador[]>) => response.body)
      )
      .subscribe((res: ITrabajador[]) => (this.trabajadors = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(pedido: IPedido) {
    this.editForm.patchValue({
      id: pedido.id,
      precioFinal: pedido.precioFinal,
      fecha: pedido.fecha,
      estadoPedidoId: pedido.estadoPedidoId,
      clienteId: pedido.clienteId,
      trabajadorId: pedido.trabajadorId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pedido = this.createFromForm();
    if (pedido.id !== undefined) {
      this.subscribeToSaveResponse(this.pedidoService.update(pedido));
    } else {
      this.subscribeToSaveResponse(this.pedidoService.create(pedido));
    }
  }

  private createFromForm(): IPedido {
    const entity = {
      ...new Pedido(),
      id: this.editForm.get(['id']).value,
      precioFinal: this.editForm.get(['precioFinal']).value,
      fecha: this.editForm.get(['fecha']).value,
      estadoPedidoId: this.editForm.get(['estadoPedidoId']).value,
      clienteId: this.editForm.get(['clienteId']).value,
      trabajadorId: this.editForm.get(['trabajadorId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPedido>>) {
    result.subscribe((res: HttpResponse<IPedido>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackEstadoPedidoById(index: number, item: IEstadoPedido) {
    return item.id;
  }

  trackClienteById(index: number, item: ICliente) {
    return item.id;
  }

  trackTrabajadorById(index: number, item: ITrabajador) {
    return item.id;
  }
}
