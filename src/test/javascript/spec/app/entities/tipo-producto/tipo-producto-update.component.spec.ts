/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { MelitBurguerTestModule } from '../../../test.module';
import { TipoProductoUpdateComponent } from 'app/entities/tipo-producto/tipo-producto-update.component';
import { TipoProductoService } from 'app/entities/tipo-producto/tipo-producto.service';
import { TipoProducto } from 'app/shared/model/tipo-producto.model';

describe('Component Tests', () => {
  describe('TipoProducto Management Update Component', () => {
    let comp: TipoProductoUpdateComponent;
    let fixture: ComponentFixture<TipoProductoUpdateComponent>;
    let service: TipoProductoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MelitBurguerTestModule],
        declarations: [TipoProductoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoProductoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoProductoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoProductoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoProducto(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoProducto();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
