import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeSortieUpdateComponent } from 'app/entities/type-sortie/type-sortie-update.component';
import { TypeSortieService } from 'app/entities/type-sortie/type-sortie.service';
import { TypeSortie } from 'app/shared/model/type-sortie.model';

describe('Component Tests', () => {
  describe('TypeSortie Management Update Component', () => {
    let comp: TypeSortieUpdateComponent;
    let fixture: ComponentFixture<TypeSortieUpdateComponent>;
    let service: TypeSortieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeSortieUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeSortieUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeSortieUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeSortieService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeSortie(123);
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
        const entity = new TypeSortie();
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
