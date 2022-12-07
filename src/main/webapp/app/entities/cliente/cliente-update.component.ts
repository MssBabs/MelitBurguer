import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICliente, Cliente } from 'app/shared/model/cliente.model';
import { ClienteService } from './cliente.service';
import { IPedido } from 'app/shared/model/pedido.model';
import { PedidoService } from 'app/entities/pedido';
import { IClienteRecompensa } from 'app/shared/model/cliente-recompensa.model';
import { ClienteRecompensaService } from 'app/entities/cliente-recompensa';

@Component({
  selector: 'jhi-cliente-update',
  templateUrl: './cliente-update.component.html'
})
export class ClienteUpdateComponent implements OnInit {
  cliente: ICliente;
  isSaving: boolean;

  pedidos: IPedido[];

  clienterecompensas: IClienteRecompensa[];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    apellido: [],
    telefono: [],
    correo: [],
    puntos: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected clienteService: ClienteService,
    protected pedidoService: PedidoService,
    protected clienteRecompensaService: ClienteRecompensaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cliente }) => {
      this.updateForm(cliente);
      this.cliente = cliente;
    });
    this.pedidoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPedido[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPedido[]>) => response.body)
      )
      .subscribe((res: IPedido[]) => (this.pedidos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.clienteRecompensaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IClienteRecompensa[]>) => mayBeOk.ok),
        map((response: HttpResponse<IClienteRecompensa[]>) => response.body)
      )
      .subscribe((res: IClienteRecompensa[]) => (this.clienterecompensas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(cliente: ICliente) {
    this.editForm.patchValue({
      id: cliente.id,
      nombre: cliente.nombre,
      apellido: cliente.apellido,
      telefono: cliente.telefono,
      correo: cliente.correo,
      puntos: cliente.puntos
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cliente = this.createFromForm();
    if (cliente.id !== undefined) {
      this.subscribeToSaveResponse(this.clienteService.update(cliente));
    } else {
      this.subscribeToSaveResponse(this.clienteService.create(cliente));
    }
  }

  private createFromForm(): ICliente {
    const entity = {
      ...new Cliente(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      apellido: this.editForm.get(['apellido']).value,
      telefono: this.editForm.get(['telefono']).value,
      correo: this.editForm.get(['correo']).value,
      puntos: this.editForm.get(['puntos']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>) {
    result.subscribe((res: HttpResponse<ICliente>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackPedidoById(index: number, item: IPedido) {
    return item.id;
  }

  trackClienteRecompensaById(index: number, item: IClienteRecompensa) {
    return item.id;
  }
}
