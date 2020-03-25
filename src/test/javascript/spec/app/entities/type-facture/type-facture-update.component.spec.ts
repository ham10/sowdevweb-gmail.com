import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeFactureUpdateComponent } from 'app/entities/type-facture/type-facture-update.component';
import { TypeFactureService } from 'app/entities/type-facture/type-facture.service';
import { TypeFacture } from 'app/shared/model/type-facture.model';

describe('Component Tests', () => {
  describe('TypeFacture Management Update Component', () => {
    let comp: TypeFactureUpdateComponent;
    let fixture: ComponentFixture<TypeFactureUpdateComponent>;
    let service: TypeFactureService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeFactureUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeFactureUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeFactureUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeFactureService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeFacture(123);
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
        const entity = new TypeFacture();
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
