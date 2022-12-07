/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MelitBurguerTestModule } from '../../../test.module';
import { TrabajadorDetailComponent } from 'app/entities/trabajador/trabajador-detail.component';
import { Trabajador } from 'app/shared/model/trabajador.model';

describe('Component Tests', () => {
  describe('Trabajador Management Detail Component', () => {
    let comp: TrabajadorDetailComponent;
    let fixture: ComponentFixture<TrabajadorDetailComponent>;
    const route = ({ data: of({ trabajador: new Trabajador(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MelitBurguerTestModule],
        declarations: [TrabajadorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TrabajadorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TrabajadorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.trabajador).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
