import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { CatReportDetailComponent } from 'app/entities/cat-report/cat-report-detail.component';
import { CatReport } from 'app/shared/model/cat-report.model';

describe('Component Tests', () => {
  describe('CatReport Management Detail Component', () => {
    let comp: CatReportDetailComponent;
    let fixture: ComponentFixture<CatReportDetailComponent>;
    const route = ({ data: of({ catReport: new CatReport(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [CatReportDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CatReportDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CatReportDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load catReport on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.catReport).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
