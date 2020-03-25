import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeReportUpdateComponent } from 'app/entities/type-report/type-report-update.component';
import { TypeReportService } from 'app/entities/type-report/type-report.service';
import { TypeReport } from 'app/shared/model/type-report.model';

describe('Component Tests', () => {
  describe('TypeReport Management Update Component', () => {
    let comp: TypeReportUpdateComponent;
    let fixture: ComponentFixture<TypeReportUpdateComponent>;
    let service: TypeReportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeReportUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeReportUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeReportUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeReportService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeReport(123);
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
        const entity = new TypeReport();
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
