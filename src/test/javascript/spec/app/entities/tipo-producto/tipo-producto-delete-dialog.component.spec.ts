/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MelitBurguerTestModule } from '../../../test.module';
import { TipoProductoDeleteDialogComponent } from 'app/entities/tipo-producto/tipo-producto-delete-dialog.component';
import { TipoProductoService } from 'app/entities/tipo-producto/tipo-producto.service';

describe('Component Tests', () => {
  describe('TipoProducto Management Delete Component', () => {
    let comp: TipoProductoDeleteDialogComponent;
    let fixture: ComponentFixture<TipoProductoDeleteDialogComponent>;
    let service: TipoProductoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MelitBurguerTestModule],
        declarations: [TipoProductoDeleteDialogComponent]
      })
        .overrideTemplate(TipoProductoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoProductoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoProductoService);
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
