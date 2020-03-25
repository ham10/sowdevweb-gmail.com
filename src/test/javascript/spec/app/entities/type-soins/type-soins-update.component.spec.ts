import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeSoinsUpdateComponent } from 'app/entities/type-soins/type-soins-update.component';
import { TypeSoinsService } from 'app/entities/type-soins/type-soins.service';
import { TypeSoins } from 'app/shared/model/type-soins.model';

describe('Component Tests', () => {
  describe('TypeSoins Management Update Component', () => {
    let comp: TypeSoinsUpdateComponent;
    let fixture: ComponentFixture<TypeSoinsUpdateComponent>;
    let service: TypeSoinsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeSoinsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeSoinsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeSoinsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeSoinsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeSoins(123);
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
        const entity = new TypeSoins();
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
