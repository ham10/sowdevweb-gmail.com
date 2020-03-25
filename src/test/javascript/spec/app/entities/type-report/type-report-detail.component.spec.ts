import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeReportDetailComponent } from 'app/entities/type-report/type-report-detail.component';
import { TypeReport } from 'app/shared/model/type-report.model';

describe('Component Tests', () => {
  describe('TypeReport Management Detail Component', () => {
    let comp: TypeReportDetailComponent;
    let fixture: ComponentFixture<TypeReportDetailComponent>;
    const route = ({ data: of({ typeReport: new TypeReport(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeReportDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeReportDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeReportDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeReport on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeReport).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
