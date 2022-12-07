/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MelitBurguerTestModule } from '../../../test.module';
import { ProductosPedidoDeleteDialogComponent } from 'app/entities/productos-pedido/productos-pedido-delete-dialog.component';
import { ProductosPedidoService } from 'app/entities/productos-pedido/productos-pedido.service';

describe('Component Tests', () => {
  describe('ProductosPedido Management Delete Component', () => {
    let comp: ProductosPedidoDeleteDialogComponent;
    let fixture: ComponentFixture<ProductosPedidoDeleteDialogComponent>;
    let service: ProductosPedidoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MelitBurguerTestModule],
        declarations: [ProductosPedidoDeleteDialogComponent]
      })
        .overrideTemplate(ProductosPedidoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductosPedidoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductosPedidoService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
