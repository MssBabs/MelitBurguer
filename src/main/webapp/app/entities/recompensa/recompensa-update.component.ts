import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRecompensa, Recompensa } from 'app/shared/model/recompensa.model';
import { RecompensaService } from './recompensa.service';
import { IClienteRecompensa } from 'app/shared/model/cliente-recompensa.model';
import { ClienteRecompensaService } from 'app/entities/cliente-recompensa';

@Component({
  selector: 'jhi-recompensa-update',
  templateUrl: './recompensa-update.component.html'
})
export class RecompensaUpdateComponent implements OnInit {
  recompensa: IRecompensa;
  isSaving: boolean;

  clienterecompensas: IClienteRecompensa[];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    descripcion: [],
    puntos: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected recompensaService: RecompensaService,
    protected clienteRecompensaService: ClienteRecompensaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ recompensa }) => {
      this.updateForm(recompensa);
      this.recompensa = recompensa;
    });
    this.clienteRecompensaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IClienteRecompensa[]>) => mayBeOk.ok),
        map((response: HttpResponse<IClienteRecompensa[]>) => response.body)
      )
      .subscribe((res: IClienteRecompensa[]) => (this.clienterecompensas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(recompensa: IRecompensa) {
    this.editForm.patchValue({
      id: recompensa.id,
      nombre: recompensa.nombre,
      descripcion: recompensa.descripcion,
      puntos: recompensa.puntos
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const recompensa = this.createFromForm();
    if (recompensa.id !== undefined) {
      this.subscribeToSaveResponse(this.recompensaService.update(recompensa));
    } else {
      this.subscribeToSaveResponse(this.recompensaService.create(recompensa));
    }
  }

  private createFromForm(): IRecompensa {
    const entity = {
      ...new Recompensa(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      puntos: this.editForm.get(['puntos']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecompensa>>) {
    result.subscribe((res: HttpResponse<IRecompensa>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackClienteRecompensaById(index: number, item: IClienteRecompensa) {
    return item.id;
  }
}
