import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { ActeMedicalUpdateComponent } from 'app/entities/acte-medical/acte-medical-update.component';
import { ActeMedicalService } from 'app/entities/acte-medical/acte-medical.service';
import { ActeMedical } from 'app/shared/model/acte-medical.model';

describe('Component Tests', () => {
  describe('ActeMedical Management Update Component', () => {
    let comp: ActeMedicalUpdateComponent;
    let fixture: ComponentFixture<ActeMedicalUpdateComponent>;
    let service: ActeMedicalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [ActeMedicalUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ActeMedicalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActeMedicalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ActeMedicalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ActeMedical(123);
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
        const entity = new ActeMedical();
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
