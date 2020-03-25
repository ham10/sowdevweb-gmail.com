import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeCaisseUpdateComponent } from 'app/entities/type-caisse/type-caisse-update.component';
import { TypeCaisseService } from 'app/entities/type-caisse/type-caisse.service';
import { TypeCaisse } from 'app/shared/model/type-caisse.model';

describe('Component Tests', () => {
  describe('TypeCaisse Management Update Component', () => {
    let comp: TypeCaisseUpdateComponent;
    let fixture: ComponentFixture<TypeCaisseUpdateComponent>;
    let service: TypeCaisseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeCaisseUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeCaisseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeCaisseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeCaisseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeCaisse(123);
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
        const entity = new TypeCaisse();
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
