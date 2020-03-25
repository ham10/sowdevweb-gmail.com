import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { JourUpdateComponent } from 'app/entities/jour/jour-update.component';
import { JourService } from 'app/entities/jour/jour.service';
import { Jour } from 'app/shared/model/jour.model';

describe('Component Tests', () => {
  describe('Jour Management Update Component', () => {
    let comp: JourUpdateComponent;
    let fixture: ComponentFixture<JourUpdateComponent>;
    let service: JourService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [JourUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(JourUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JourUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JourService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Jour(123);
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
        const entity = new Jour();
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
