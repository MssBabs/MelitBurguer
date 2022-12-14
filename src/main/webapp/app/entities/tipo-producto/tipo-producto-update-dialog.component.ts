import { NgbActiveModal, NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { Component, Input, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITipoProducto, TipoProducto } from 'app/shared/model/tipo-producto.model';
import { TipoProductoService } from './tipo-producto.service';

@Component({
  selector: 'jhi-tipo-producto-update-dialog',
  templateUrl: './tipo-producto-update-dialog.component.html'
})
export class TipoProductoUpdatePopupComponent implements OnInit {
  @Input() tipoProductos: ITipoProducto;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    descripcion: []
  });

  constructor(
    protected tipoProductoService: TipoProductoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    public activeModal: NgbActiveModal
  ) {}

  ngOnInit() {
    this.updateForm(this.tipoProductos);
    //console.log(this.tipoProducto);
  }

  updateForm(tipoProductos: ITipoProducto) {
    console.log(this.tipoProductos);
    this.editForm.patchValue({
      id: tipoProductos.id,
      nombre: tipoProductos.nombre,
      descripcion: tipoProductos.descripcion
    });
  }

  previousState() {
    window.history.back();

    this.activeModal.close();
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
    //console.log(entity);
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
