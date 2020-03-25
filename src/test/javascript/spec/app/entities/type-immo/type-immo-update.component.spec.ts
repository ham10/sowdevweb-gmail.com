import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeImmoUpdateComponent } from 'app/entities/type-immo/type-immo-update.component';
import { TypeImmoService } from 'app/entities/type-immo/type-immo.service';
import { TypeImmo } from 'app/shared/model/type-immo.model';

describe('Component Tests', () => {
  describe('TypeImmo Management Update Component', () => {
    let comp: TypeImmoUpdateComponent;
    let fixture: ComponentFixture<TypeImmoUpdateComponent>;
    let service: TypeImmoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeImmoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeImmoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeImmoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeImmoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeImmo(123);
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
        const entity = new TypeImmo();
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
