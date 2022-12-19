import { Component, Input, OnInit } from '@angular/core';
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
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-recompensa-update-dialog',
  templateUrl: './recompensa-update-dialog.component.html'
})
export class RecompensaUpdatePopupComponent implements OnInit {
  @Input() recompensa: IRecompensa;
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
    private fb: FormBuilder,
    public activeModal: NgbActiveModal
  ) {}

  ngOnInit() {
    this.updateForm(this.recompensa);
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
    this.activeModal.close();
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
