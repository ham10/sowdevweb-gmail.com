import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { DeptServicesUpdateComponent } from 'app/entities/dept-services/dept-services-update.component';
import { DeptServicesService } from 'app/entities/dept-services/dept-services.service';
import { DeptServices } from 'app/shared/model/dept-services.model';

describe('Component Tests', () => {
  describe('DeptServices Management Update Component', () => {
    let comp: DeptServicesUpdateComponent;
    let fixture: ComponentFixture<DeptServicesUpdateComponent>;
    let service: DeptServicesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [DeptServicesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DeptServicesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeptServicesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeptServicesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DeptServices(123);
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
        const entity = new DeptServices();
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
