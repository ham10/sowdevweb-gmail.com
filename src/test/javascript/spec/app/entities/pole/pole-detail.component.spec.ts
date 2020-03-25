import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { PoleDetailComponent } from 'app/entities/pole/pole-detail.component';
import { Pole } from 'app/shared/model/pole.model';

describe('Component Tests', () => {
  describe('Pole Management Detail Component', () => {
    let comp: PoleDetailComponent;
    let fixture: ComponentFixture<PoleDetailComponent>;
    const route = ({ data: of({ pole: new Pole(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [PoleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PoleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PoleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pole on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pole).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
