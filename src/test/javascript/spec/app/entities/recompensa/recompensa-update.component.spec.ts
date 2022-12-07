/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { MelitBurguerTestModule } from '../../../test.module';
import { RecompensaUpdateComponent } from 'app/entities/recompensa/recompensa-update.component';
import { RecompensaService } from 'app/entities/recompensa/recompensa.service';
import { Recompensa } from 'app/shared/model/recompensa.model';

describe('Component Tests', () => {
  describe('Recompensa Management Update Component', () => {
    let comp: RecompensaUpdateComponent;
    let fixture: ComponentFixture<RecompensaUpdateComponent>;
    let service: RecompensaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MelitBurguerTestModule],
        declarations: [RecompensaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RecompensaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RecompensaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecompensaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Recompensa(123);
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
        const entity = new Recompensa();
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
