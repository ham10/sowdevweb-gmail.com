import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeUniteUpdateComponent } from 'app/entities/type-unite/type-unite-update.component';
import { TypeUniteService } from 'app/entities/type-unite/type-unite.service';
import { TypeUnite } from 'app/shared/model/type-unite.model';

describe('Component Tests', () => {
  describe('TypeUnite Management Update Component', () => {
    let comp: TypeUniteUpdateComponent;
    let fixture: ComponentFixture<TypeUniteUpdateComponent>;
    let service: TypeUniteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeUniteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeUniteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeUniteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeUniteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeUnite(123);
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
        const entity = new TypeUnite();
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
