import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeChampsUpdateComponent } from 'app/entities/type-champs/type-champs-update.component';
import { TypeChampsService } from 'app/entities/type-champs/type-champs.service';
import { TypeChamps } from 'app/shared/model/type-champs.model';

describe('Component Tests', () => {
  describe('TypeChamps Management Update Component', () => {
    let comp: TypeChampsUpdateComponent;
    let fixture: ComponentFixture<TypeChampsUpdateComponent>;
    let service: TypeChampsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeChampsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeChampsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeChampsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeChampsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeChamps(123);
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
        const entity = new TypeChamps();
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
