import { Component, Input, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITrabajador, Trabajador } from 'app/shared/model/trabajador.model';
import { TrabajadorService } from './trabajador.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-trabajador-update-dialog',
  templateUrl: './trabajador-update-dialog.component.html'
})
export class TrabajadorUpdatePopupComponent implements OnInit {
  @Input() trabajador: ITrabajador;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    apellido: [],
    dni: [],
    telefono: [],
    correo: []
  });

  constructor(
    protected trabajadorService: TrabajadorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    public activeModal: NgbActiveModal
  ) {}

  ngOnInit() {
    this.updateForm(this.trabajador);
  }

  updateForm(trabajador: ITrabajador) {
    this.editForm.patchValue({
      id: trabajador.id,
      nombre: trabajador.nombre,
      apellido: trabajador.apellido,
      dni: trabajador.dni,
      telefono: trabajador.telefono,
      correo: trabajador.correo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const trabajador = this.createFromForm();
    if (trabajador.id !== undefined) {
      this.subscribeToSaveResponse(this.trabajadorService.update(trabajador));
    }
  }

  private createFromForm(): ITrabajador {
    const entity = {
      ...new Trabajador(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      apellido: this.editForm.get(['apellido']).value,
      dni: this.editForm.get(['dni']).value,
      telefono: this.editForm.get(['telefono']).value,
      correo: this.editForm.get(['correo']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrabajador>>) {
    result.subscribe((res: HttpResponse<ITrabajador>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.activeModal.close();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
