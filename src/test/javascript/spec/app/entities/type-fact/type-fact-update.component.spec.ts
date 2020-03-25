import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeFactUpdateComponent } from 'app/entities/type-fact/type-fact-update.component';
import { TypeFactService } from 'app/entities/type-fact/type-fact.service';
import { TypeFact } from 'app/shared/model/type-fact.model';

describe('Component Tests', () => {
  describe('TypeFact Management Update Component', () => {
    let comp: TypeFactUpdateComponent;
    let fixture: ComponentFixture<TypeFactUpdateComponent>;
    let service: TypeFactService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeFactUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeFactUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeFactUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeFactService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeFact(123);
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
        const entity = new TypeFact();
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
