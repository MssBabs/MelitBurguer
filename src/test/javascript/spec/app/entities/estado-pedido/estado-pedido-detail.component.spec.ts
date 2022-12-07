/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MelitBurguerTestModule } from '../../../test.module';
import { EstadoPedidoDetailComponent } from 'app/entities/estado-pedido/estado-pedido-detail.component';
import { EstadoPedido } from 'app/shared/model/estado-pedido.model';

describe('Component Tests', () => {
  describe('EstadoPedido Management Detail Component', () => {
    let comp: EstadoPedidoDetailComponent;
    let fixture: ComponentFixture<EstadoPedidoDetailComponent>;
    const route = ({ data: of({ estadoPedido: new EstadoPedido(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MelitBurguerTestModule],
        declarations: [EstadoPedidoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EstadoPedidoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstadoPedidoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.estadoPedido).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
