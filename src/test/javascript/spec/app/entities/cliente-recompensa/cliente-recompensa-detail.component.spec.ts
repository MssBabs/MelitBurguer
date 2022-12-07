/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MelitBurguerTestModule } from '../../../test.module';
import { ClienteRecompensaDetailComponent } from 'app/entities/cliente-recompensa/cliente-recompensa-detail.component';
import { ClienteRecompensa } from 'app/shared/model/cliente-recompensa.model';

describe('Component Tests', () => {
  describe('ClienteRecompensa Management Detail Component', () => {
    let comp: ClienteRecompensaDetailComponent;
    let fixture: ComponentFixture<ClienteRecompensaDetailComponent>;
    const route = ({ data: of({ clienteRecompensa: new ClienteRecompensa(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MelitBurguerTestModule],
        declarations: [ClienteRecompensaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ClienteRecompensaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClienteRecompensaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.clienteRecompensa).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
