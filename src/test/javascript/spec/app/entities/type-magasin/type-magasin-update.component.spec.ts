import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeMagasinUpdateComponent } from 'app/entities/type-magasin/type-magasin-update.component';
import { TypeMagasinService } from 'app/entities/type-magasin/type-magasin.service';
import { TypeMagasin } from 'app/shared/model/type-magasin.model';

describe('Component Tests', () => {
  describe('TypeMagasin Management Update Component', () => {
    let comp: TypeMagasinUpdateComponent;
    let fixture: ComponentFixture<TypeMagasinUpdateComponent>;
    let service: TypeMagasinService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeMagasinUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeMagasinUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeMagasinUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeMagasinService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeMagasin(123);
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
        const entity = new TypeMagasin();
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
