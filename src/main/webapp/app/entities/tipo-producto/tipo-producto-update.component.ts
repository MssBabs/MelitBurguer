import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITipoProducto, TipoProducto } from 'app/shared/model/tipo-producto.model';
import { TipoProductoService } from './tipo-producto.service';

@Component({
  selector: 'jhi-tipo-producto-update',
  templateUrl: './tipo-producto-update.component.html'
})
export class TipoProductoUpdateComponent implements OnInit {
  tipoProducto: ITipoProducto;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    descripcion: []
  });

  constructor(protected tipoProductoService: TipoProductoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tipoProducto }) => {
      this.updateForm(tipoProducto);
      this.tipoProducto = tipoProducto;
    });
  }

  updateForm(tipoProducto: ITipoProducto) {
    this.editForm.patchValue({
      id: tipoProducto.id,
      nombre: tipoProducto.nombre,
      descripcion: tipoProducto.descripcion
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tipoProducto = this.createFromForm();
    if (tipoProducto.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoProductoService.update(tipoProducto));
    } else {
      this.subscribeToSaveResponse(this.tipoProductoService.create(tipoProducto));
    }
  }

  private createFromForm(): ITipoProducto {
    const entity = {
      ...new TipoProducto(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoProducto>>) {
    result.subscribe((res: HttpResponse<ITipoProducto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
