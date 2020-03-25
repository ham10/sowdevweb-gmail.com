import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypePoleUpdateComponent } from 'app/entities/type-pole/type-pole-update.component';
import { TypePoleService } from 'app/entities/type-pole/type-pole.service';
import { TypePole } from 'app/shared/model/type-pole.model';

describe('Component Tests', () => {
  describe('TypePole Management Update Component', () => {
    let comp: TypePoleUpdateComponent;
    let fixture: ComponentFixture<TypePoleUpdateComponent>;
    let service: TypePoleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypePoleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypePoleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypePoleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypePoleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypePole(123);
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
        const entity = new TypePole();
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
