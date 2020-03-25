import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { JourFerieDetailComponent } from 'app/entities/jour-ferie/jour-ferie-detail.component';
import { JourFerie } from 'app/shared/model/jour-ferie.model';

describe('Component Tests', () => {
  describe('JourFerie Management Detail Component', () => {
    let comp: JourFerieDetailComponent;
    let fixture: ComponentFixture<JourFerieDetailComponent>;
    const route = ({ data: of({ jourFerie: new JourFerie(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [JourFerieDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(JourFerieDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JourFerieDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load jourFerie on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jourFerie).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
