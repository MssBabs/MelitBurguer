/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { MelitBurguerTestModule } from '../../../test.module';
import { TrabajadorUpdateComponent } from 'app/entities/trabajador/trabajador-update.component';
import { TrabajadorService } from 'app/entities/trabajador/trabajador.service';
import { Trabajador } from 'app/shared/model/trabajador.model';

describe('Component Tests', () => {
  describe('Trabajador Management Update Component', () => {
    let comp: TrabajadorUpdateComponent;
    let fixture: ComponentFixture<TrabajadorUpdateComponent>;
    let service: TrabajadorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MelitBurguerTestModule],
        declarations: [TrabajadorUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TrabajadorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TrabajadorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrabajadorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Trabajador(123);
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
        const entity = new Trabajador();
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
