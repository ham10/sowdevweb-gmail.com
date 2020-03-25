import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { VaccinUpdateComponent } from 'app/entities/vaccin/vaccin-update.component';
import { VaccinService } from 'app/entities/vaccin/vaccin.service';
import { Vaccin } from 'app/shared/model/vaccin.model';

describe('Component Tests', () => {
  describe('Vaccin Management Update Component', () => {
    let comp: VaccinUpdateComponent;
    let fixture: ComponentFixture<VaccinUpdateComponent>;
    let service: VaccinService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [VaccinUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VaccinUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VaccinUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VaccinService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Vaccin(123);
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
        const entity = new Vaccin();
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
