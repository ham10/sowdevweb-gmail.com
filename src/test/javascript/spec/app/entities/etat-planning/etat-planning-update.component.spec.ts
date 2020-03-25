import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { EtatPlanningUpdateComponent } from 'app/entities/etat-planning/etat-planning-update.component';
import { EtatPlanningService } from 'app/entities/etat-planning/etat-planning.service';
import { EtatPlanning } from 'app/shared/model/etat-planning.model';

describe('Component Tests', () => {
  describe('EtatPlanning Management Update Component', () => {
    let comp: EtatPlanningUpdateComponent;
    let fixture: ComponentFixture<EtatPlanningUpdateComponent>;
    let service: EtatPlanningService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [EtatPlanningUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EtatPlanningUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EtatPlanningUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EtatPlanningService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EtatPlanning(123);
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
        const entity = new EtatPlanning();
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
