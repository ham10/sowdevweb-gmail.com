import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeCondUpdateComponent } from 'app/entities/type-cond/type-cond-update.component';
import { TypeCondService } from 'app/entities/type-cond/type-cond.service';
import { TypeCond } from 'app/shared/model/type-cond.model';

describe('Component Tests', () => {
  describe('TypeCond Management Update Component', () => {
    let comp: TypeCondUpdateComponent;
    let fixture: ComponentFixture<TypeCondUpdateComponent>;
    let service: TypeCondService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeCondUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeCondUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeCondUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeCondService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeCond(123);
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
        const entity = new TypeCond();
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
