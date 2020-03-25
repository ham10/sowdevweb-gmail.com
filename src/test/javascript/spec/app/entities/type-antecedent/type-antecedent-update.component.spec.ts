import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeAntecedentUpdateComponent } from 'app/entities/type-antecedent/type-antecedent-update.component';
import { TypeAntecedentService } from 'app/entities/type-antecedent/type-antecedent.service';
import { TypeAntecedent } from 'app/shared/model/type-antecedent.model';

describe('Component Tests', () => {
  describe('TypeAntecedent Management Update Component', () => {
    let comp: TypeAntecedentUpdateComponent;
    let fixture: ComponentFixture<TypeAntecedentUpdateComponent>;
    let service: TypeAntecedentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeAntecedentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeAntecedentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeAntecedentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeAntecedentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeAntecedent(123);
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
        const entity = new TypeAntecedent();
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
