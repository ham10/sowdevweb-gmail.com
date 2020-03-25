import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { ChapComptaDetailComponent } from 'app/entities/chap-compta/chap-compta-detail.component';
import { ChapCompta } from 'app/shared/model/chap-compta.model';

describe('Component Tests', () => {
  describe('ChapCompta Management Detail Component', () => {
    let comp: ChapComptaDetailComponent;
    let fixture: ComponentFixture<ChapComptaDetailComponent>;
    const route = ({ data: of({ chapCompta: new ChapCompta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [ChapComptaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ChapComptaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ChapComptaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load chapCompta on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.chapCompta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
