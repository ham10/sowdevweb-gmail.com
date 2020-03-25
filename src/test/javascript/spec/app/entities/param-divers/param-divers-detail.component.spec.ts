import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { ParamDiversDetailComponent } from 'app/entities/param-divers/param-divers-detail.component';
import { ParamDivers } from 'app/shared/model/param-divers.model';

describe('Component Tests', () => {
  describe('ParamDivers Management Detail Component', () => {
    let comp: ParamDiversDetailComponent;
    let fixture: ComponentFixture<ParamDiversDetailComponent>;
    const route = ({ data: of({ paramDivers: new ParamDivers(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [ParamDiversDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ParamDiversDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ParamDiversDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load paramDivers on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paramDivers).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
