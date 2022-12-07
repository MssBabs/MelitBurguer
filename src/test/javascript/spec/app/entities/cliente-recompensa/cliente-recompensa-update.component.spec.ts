/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { MelitBurguerTestModule } from '../../../test.module';
import { ClienteRecompensaUpdateComponent } from 'app/entities/cliente-recompensa/cliente-recompensa-update.component';
import { ClienteRecompensaService } from 'app/entities/cliente-recompensa/cliente-recompensa.service';
import { ClienteRecompensa } from 'app/shared/model/cliente-recompensa.model';

describe('Component Tests', () => {
  describe('ClienteRecompensa Management Update Component', () => {
    let comp: ClienteRecompensaUpdateComponent;
    let fixture: ComponentFixture<ClienteRecompensaUpdateComponent>;
    let service: ClienteRecompensaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MelitBurguerTestModule],
        declarations: [ClienteRecompensaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ClienteRecompensaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClienteRecompensaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClienteRecompensaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ClienteRecompensa(123);
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
        const entity = new ClienteRecompensa();
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
