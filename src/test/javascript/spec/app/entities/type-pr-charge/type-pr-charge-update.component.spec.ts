import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypePrChargeUpdateComponent } from 'app/entities/type-pr-charge/type-pr-charge-update.component';
import { TypePrChargeService } from 'app/entities/type-pr-charge/type-pr-charge.service';
import { TypePrCharge } from 'app/shared/model/type-pr-charge.model';

describe('Component Tests', () => {
  describe('TypePrCharge Management Update Component', () => {
    let comp: TypePrChargeUpdateComponent;
    let fixture: ComponentFixture<TypePrChargeUpdateComponent>;
    let service: TypePrChargeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypePrChargeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypePrChargeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypePrChargeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypePrChargeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypePrCharge(123);
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
        const entity = new TypePrCharge();
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
