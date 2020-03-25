import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeLitUpdateComponent } from 'app/entities/type-lit/type-lit-update.component';
import { TypeLitService } from 'app/entities/type-lit/type-lit.service';
import { TypeLit } from 'app/shared/model/type-lit.model';

describe('Component Tests', () => {
  describe('TypeLit Management Update Component', () => {
    let comp: TypeLitUpdateComponent;
    let fixture: ComponentFixture<TypeLitUpdateComponent>;
    let service: TypeLitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeLitUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeLitUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeLitUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeLitService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeLit(123);
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
        const entity = new TypeLit();
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
