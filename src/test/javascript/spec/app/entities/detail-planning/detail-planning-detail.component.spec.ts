import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { DetailPlanningDetailComponent } from 'app/entities/detail-planning/detail-planning-detail.component';
import { DetailPlanning } from 'app/shared/model/detail-planning.model';

describe('Component Tests', () => {
  describe('DetailPlanning Management Detail Component', () => {
    let comp: DetailPlanningDetailComponent;
    let fixture: ComponentFixture<DetailPlanningDetailComponent>;
    const route = ({ data: of({ detailPlanning: new DetailPlanning(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [DetailPlanningDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DetailPlanningDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DetailPlanningDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load detailPlanning on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.detailPlanning).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
