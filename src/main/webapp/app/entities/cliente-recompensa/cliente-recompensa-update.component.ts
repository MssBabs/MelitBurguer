import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IClienteRecompensa, ClienteRecompensa } from 'app/shared/model/cliente-recompensa.model';
import { ClienteRecompensaService } from './cliente-recompensa.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';
import { IRecompensa } from 'app/shared/model/recompensa.model';
import { RecompensaService } from 'app/entities/recompensa';

@Component({
  selector: 'jhi-cliente-recompensa-update',
  templateUrl: './cliente-recompensa-update.component.html'
})
export class ClienteRecompensaUpdateComponent implements OnInit {
  clienteRecompensa: IClienteRecompensa;
  isSaving: boolean;

  clientes: ICliente[];

  recompensas: IRecompensa[];
  fechaDp: any;

  editForm = this.fb.group({
    id: [],
    fecha: [],
    clienteId: [],
    recompensaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected clienteRecompensaService: ClienteRecompensaService,
    protected clienteService: ClienteService,
    protected recompensaService: RecompensaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ clienteRecompensa }) => {
      this.updateForm(clienteRecompensa);
      this.clienteRecompensa = clienteRecompensa;
    });
    this.clienteService
      .query({ filter: 'clienterecompensa-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ICliente[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICliente[]>) => response.body)
      )
      .subscribe(
        (res: ICliente[]) => {
          if (!this.clienteRecompensa.clienteId) {
            this.clientes = res;
          } else {
            this.clienteService
              .find(this.clienteRecompensa.clienteId)
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
    this.recompensaService
      .query({ filter: 'clienterecompensa-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IRecompensa[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRecompensa[]>) => response.body)
      )
      .subscribe(
        (res: IRecompensa[]) => {
          if (!this.clienteRecompensa.recompensaId) {
            this.recompensas = res;
          } else {
            this.recompensaService
              .find(this.clienteRecompensa.recompensaId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IRecompensa>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IRecompensa>) => subResponse.body)
              )
              .subscribe(
                (subRes: IRecompensa) => (this.recompensas = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(clienteRecompensa: IClienteRecompensa) {
    this.editForm.patchValue({
      id: clienteRecompensa.id,
      fecha: clienteRecompensa.fecha,
      clienteId: clienteRecompensa.clienteId,
      recompensaId: clienteRecompensa.recompensaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const clienteRecompensa = this.createFromForm();
    if (clienteRecompensa.id !== undefined) {
      this.subscribeToSaveResponse(this.clienteRecompensaService.update(clienteRecompensa));
    } else {
      this.subscribeToSaveResponse(this.clienteRecompensaService.create(clienteRecompensa));
    }
  }

  private createFromForm(): IClienteRecompensa {
    const entity = {
      ...new ClienteRecompensa(),
      id: this.editForm.get(['id']).value,
      fecha: this.editForm.get(['fecha']).value,
      clienteId: this.editForm.get(['clienteId']).value,
      recompensaId: this.editForm.get(['recompensaId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClienteRecompensa>>) {
    result.subscribe((res: HttpResponse<IClienteRecompensa>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackClienteById(index: number, item: ICliente) {
    return item.id;
  }

  trackRecompensaById(index: number, item: IRecompensa) {
    return item.id;
  }
}
