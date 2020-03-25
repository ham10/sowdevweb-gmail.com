import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypePlateauUpdateComponent } from 'app/entities/type-plateau/type-plateau-update.component';
import { TypePlateauService } from 'app/entities/type-plateau/type-plateau.service';
import { TypePlateau } from 'app/shared/model/type-plateau.model';

describe('Component Tests', () => {
  describe('TypePlateau Management Update Component', () => {
    let comp: TypePlateauUpdateComponent;
    let fixture: ComponentFixture<TypePlateauUpdateComponent>;
    let service: TypePlateauService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypePlateauUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypePlateauUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypePlateauUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypePlateauService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypePlateau(123);
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
        const entity = new TypePlateau();
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
