import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { DetailPlanningUpdateComponent } from 'app/entities/detail-planning/detail-planning-update.component';
import { DetailPlanningService } from 'app/entities/detail-planning/detail-planning.service';
import { DetailPlanning } from 'app/shared/model/detail-planning.model';

describe('Component Tests', () => {
  describe('DetailPlanning Management Update Component', () => {
    let comp: DetailPlanningUpdateComponent;
    let fixture: ComponentFixture<DetailPlanningUpdateComponent>;
    let service: DetailPlanningService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [DetailPlanningUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DetailPlanningUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DetailPlanningUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DetailPlanningService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DetailPlanning(123);
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
        const entity = new DetailPlanning();
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
