import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypePlanningUpdateComponent } from 'app/entities/type-planning/type-planning-update.component';
import { TypePlanningService } from 'app/entities/type-planning/type-planning.service';
import { TypePlanning } from 'app/shared/model/type-planning.model';

describe('Component Tests', () => {
  describe('TypePlanning Management Update Component', () => {
    let comp: TypePlanningUpdateComponent;
    let fixture: ComponentFixture<TypePlanningUpdateComponent>;
    let service: TypePlanningService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypePlanningUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypePlanningUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypePlanningUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypePlanningService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypePlanning(123);
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
        const entity = new TypePlanning();
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
