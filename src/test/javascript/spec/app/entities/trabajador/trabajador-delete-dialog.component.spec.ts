/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MelitBurguerTestModule } from '../../../test.module';
import { TrabajadorDeleteDialogComponent } from 'app/entities/trabajador/trabajador-delete-dialog.component';
import { TrabajadorService } from 'app/entities/trabajador/trabajador.service';

describe('Component Tests', () => {
  describe('Trabajador Management Delete Component', () => {
    let comp: TrabajadorDeleteDialogComponent;
    let fixture: ComponentFixture<TrabajadorDeleteDialogComponent>;
    let service: TrabajadorService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MelitBurguerTestModule],
        declarations: [TrabajadorDeleteDialogComponent]
      })
        .overrideTemplate(TrabajadorDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TrabajadorDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrabajadorService);
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
