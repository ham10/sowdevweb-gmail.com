import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { AyantDroitDetailComponent } from 'app/entities/ayant-droit/ayant-droit-detail.component';
import { AyantDroit } from 'app/shared/model/ayant-droit.model';

describe('Component Tests', () => {
  describe('AyantDroit Management Detail Component', () => {
    let comp: AyantDroitDetailComponent;
    let fixture: ComponentFixture<AyantDroitDetailComponent>;
    const route = ({ data: of({ ayantDroit: new AyantDroit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [AyantDroitDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AyantDroitDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AyantDroitDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ayantDroit on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ayantDroit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
