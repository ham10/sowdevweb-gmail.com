import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TauxDeviseUpdateComponent } from 'app/entities/taux-devise/taux-devise-update.component';
import { TauxDeviseService } from 'app/entities/taux-devise/taux-devise.service';
import { TauxDevise } from 'app/shared/model/taux-devise.model';

describe('Component Tests', () => {
  describe('TauxDevise Management Update Component', () => {
    let comp: TauxDeviseUpdateComponent;
    let fixture: ComponentFixture<TauxDeviseUpdateComponent>;
    let service: TauxDeviseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TauxDeviseUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TauxDeviseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TauxDeviseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TauxDeviseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TauxDevise(123);
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
        const entity = new TauxDevise();
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
