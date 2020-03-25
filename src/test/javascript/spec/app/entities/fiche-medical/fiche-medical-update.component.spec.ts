import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { FicheMedicalUpdateComponent } from 'app/entities/fiche-medical/fiche-medical-update.component';
import { FicheMedicalService } from 'app/entities/fiche-medical/fiche-medical.service';
import { FicheMedical } from 'app/shared/model/fiche-medical.model';

describe('Component Tests', () => {
  describe('FicheMedical Management Update Component', () => {
    let comp: FicheMedicalUpdateComponent;
    let fixture: ComponentFixture<FicheMedicalUpdateComponent>;
    let service: FicheMedicalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [FicheMedicalUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FicheMedicalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FicheMedicalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FicheMedicalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FicheMedical(123);
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
        const entity = new FicheMedical();
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
