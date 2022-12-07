/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { MelitBurguerTestModule } from '../../../test.module';
import { ProductosPedidoUpdateComponent } from 'app/entities/productos-pedido/productos-pedido-update.component';
import { ProductosPedidoService } from 'app/entities/productos-pedido/productos-pedido.service';
import { ProductosPedido } from 'app/shared/model/productos-pedido.model';

describe('Component Tests', () => {
  describe('ProductosPedido Management Update Component', () => {
    let comp: ProductosPedidoUpdateComponent;
    let fixture: ComponentFixture<ProductosPedidoUpdateComponent>;
    let service: ProductosPedidoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MelitBurguerTestModule],
        declarations: [ProductosPedidoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProductosPedidoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductosPedidoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductosPedidoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductosPedido(123);
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
        const entity = new ProductosPedido();
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
