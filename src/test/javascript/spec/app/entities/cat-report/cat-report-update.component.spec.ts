import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { CatReportUpdateComponent } from 'app/entities/cat-report/cat-report-update.component';
import { CatReportService } from 'app/entities/cat-report/cat-report.service';
import { CatReport } from 'app/shared/model/cat-report.model';

describe('Component Tests', () => {
  describe('CatReport Management Update Component', () => {
    let comp: CatReportUpdateComponent;
    let fixture: ComponentFixture<CatReportUpdateComponent>;
    let service: CatReportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [CatReportUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CatReportUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CatReportUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatReportService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CatReport(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CatReport();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
