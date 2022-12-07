/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MelitBurguerTestModule } from '../../../test.module';
import { ProductosPedidoDetailComponent } from 'app/entities/productos-pedido/productos-pedido-detail.component';
import { ProductosPedido } from 'app/shared/model/productos-pedido.model';

describe('Component Tests', () => {
  describe('ProductosPedido Management Detail Component', () => {
    let comp: ProductosPedidoDetailComponent;
    let fixture: ComponentFixture<ProductosPedidoDetailComponent>;
    const route = ({ data: of({ productosPedido: new ProductosPedido(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MelitBurguerTestModule],
        declarations: [ProductosPedidoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProductosPedidoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductosPedidoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productosPedido).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
