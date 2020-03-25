import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypePatientUpdateComponent } from 'app/entities/type-patient/type-patient-update.component';
import { TypePatientService } from 'app/entities/type-patient/type-patient.service';
import { TypePatient } from 'app/shared/model/type-patient.model';

describe('Component Tests', () => {
  describe('TypePatient Management Update Component', () => {
    let comp: TypePatientUpdateComponent;
    let fixture: ComponentFixture<TypePatientUpdateComponent>;
    let service: TypePatientService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypePatientUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypePatientUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypePatientUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypePatientService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypePatient(123);
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
        const entity = new TypePatient();
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
