/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MelitBurguerTestModule } from '../../../test.module';
import { TipoProductoDetailComponent } from 'app/entities/tipo-producto/tipo-producto-detail.component';
import { TipoProducto } from 'app/shared/model/tipo-producto.model';

describe('Component Tests', () => {
  describe('TipoProducto Management Detail Component', () => {
    let comp: TipoProductoDetailComponent;
    let fixture: ComponentFixture<TipoProductoDetailComponent>;
    const route = ({ data: of({ tipoProducto: new TipoProducto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MelitBurguerTestModule],
        declarations: [TipoProductoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoProductoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoProductoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoProducto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
