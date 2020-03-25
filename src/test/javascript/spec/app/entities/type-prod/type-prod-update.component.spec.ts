import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeProdUpdateComponent } from 'app/entities/type-prod/type-prod-update.component';
import { TypeProdService } from 'app/entities/type-prod/type-prod.service';
import { TypeProd } from 'app/shared/model/type-prod.model';

describe('Component Tests', () => {
  describe('TypeProd Management Update Component', () => {
    let comp: TypeProdUpdateComponent;
    let fixture: ComponentFixture<TypeProdUpdateComponent>;
    let service: TypeProdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeProdUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeProdUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeProdUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeProdService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeProd(123);
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
        const entity = new TypeProd();
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
