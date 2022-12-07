/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { MelitBurguerTestModule } from '../../../test.module';
import { EstadoPedidoUpdateComponent } from 'app/entities/estado-pedido/estado-pedido-update.component';
import { EstadoPedidoService } from 'app/entities/estado-pedido/estado-pedido.service';
import { EstadoPedido } from 'app/shared/model/estado-pedido.model';

describe('Component Tests', () => {
  describe('EstadoPedido Management Update Component', () => {
    let comp: EstadoPedidoUpdateComponent;
    let fixture: ComponentFixture<EstadoPedidoUpdateComponent>;
    let service: EstadoPedidoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MelitBurguerTestModule],
        declarations: [EstadoPedidoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EstadoPedidoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstadoPedidoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstadoPedidoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EstadoPedido(123);
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
        const entity = new EstadoPedido();
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
