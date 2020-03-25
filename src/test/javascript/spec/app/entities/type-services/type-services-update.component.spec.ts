import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeServicesUpdateComponent } from 'app/entities/type-services/type-services-update.component';
import { TypeServicesService } from 'app/entities/type-services/type-services.service';
import { TypeServices } from 'app/shared/model/type-services.model';

describe('Component Tests', () => {
  describe('TypeServices Management Update Component', () => {
    let comp: TypeServicesUpdateComponent;
    let fixture: ComponentFixture<TypeServicesUpdateComponent>;
    let service: TypeServicesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeServicesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeServicesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeServicesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeServicesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeServices(123);
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
        const entity = new TypeServices();
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
