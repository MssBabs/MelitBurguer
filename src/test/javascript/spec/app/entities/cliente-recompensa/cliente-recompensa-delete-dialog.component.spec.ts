/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MelitBurguerTestModule } from '../../../test.module';
import { ClienteRecompensaDeleteDialogComponent } from 'app/entities/cliente-recompensa/cliente-recompensa-delete-dialog.component';
import { ClienteRecompensaService } from 'app/entities/cliente-recompensa/cliente-recompensa.service';

describe('Component Tests', () => {
  describe('ClienteRecompensa Management Delete Component', () => {
    let comp: ClienteRecompensaDeleteDialogComponent;
    let fixture: ComponentFixture<ClienteRecompensaDeleteDialogComponent>;
    let service: ClienteRecompensaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MelitBurguerTestModule],
        declarations: [ClienteRecompensaDeleteDialogComponent]
      })
        .overrideTemplate(ClienteRecompensaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClienteRecompensaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClienteRecompensaService);
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
