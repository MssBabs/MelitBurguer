/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MelitBurguerTestModule } from '../../../test.module';
import { RecompensaDetailComponent } from 'app/entities/recompensa/recompensa-detail.component';
import { Recompensa } from 'app/shared/model/recompensa.model';

describe('Component Tests', () => {
  describe('Recompensa Management Detail Component', () => {
    let comp: RecompensaDetailComponent;
    let fixture: ComponentFixture<RecompensaDetailComponent>;
    const route = ({ data: of({ recompensa: new Recompensa(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MelitBurguerTestModule],
        declarations: [RecompensaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RecompensaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RecompensaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.recompensa).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
