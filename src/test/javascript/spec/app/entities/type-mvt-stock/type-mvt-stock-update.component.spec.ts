import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeMvtStockUpdateComponent } from 'app/entities/type-mvt-stock/type-mvt-stock-update.component';
import { TypeMvtStockService } from 'app/entities/type-mvt-stock/type-mvt-stock.service';
import { TypeMvtStock } from 'app/shared/model/type-mvt-stock.model';

describe('Component Tests', () => {
  describe('TypeMvtStock Management Update Component', () => {
    let comp: TypeMvtStockUpdateComponent;
    let fixture: ComponentFixture<TypeMvtStockUpdateComponent>;
    let service: TypeMvtStockService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeMvtStockUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeMvtStockUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeMvtStockUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeMvtStockService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeMvtStock(123);
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
        const entity = new TypeMvtStock();
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
