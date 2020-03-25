import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeConstanteUpdateComponent } from 'app/entities/type-constante/type-constante-update.component';
import { TypeConstanteService } from 'app/entities/type-constante/type-constante.service';
import { TypeConstante } from 'app/shared/model/type-constante.model';

describe('Component Tests', () => {
  describe('TypeConstante Management Update Component', () => {
    let comp: TypeConstanteUpdateComponent;
    let fixture: ComponentFixture<TypeConstanteUpdateComponent>;
    let service: TypeConstanteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeConstanteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeConstanteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeConstanteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeConstanteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeConstante(123);
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
        const entity = new TypeConstante();
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
