import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { ServicesUpdateComponent } from 'app/entities/services/services-update.component';
import { ServicesService } from 'app/entities/services/services.service';
import { Services } from 'app/shared/model/services.model';

describe('Component Tests', () => {
  describe('Services Management Update Component', () => {
    let comp: ServicesUpdateComponent;
    let fixture: ComponentFixture<ServicesUpdateComponent>;
    let service: ServicesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [ServicesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServicesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServicesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServicesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Services(123);
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
        const entity = new Services();
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
