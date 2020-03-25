import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { ImmoUpdateComponent } from 'app/entities/immo/immo-update.component';
import { ImmoService } from 'app/entities/immo/immo.service';
import { Immo } from 'app/shared/model/immo.model';

describe('Component Tests', () => {
  describe('Immo Management Update Component', () => {
    let comp: ImmoUpdateComponent;
    let fixture: ComponentFixture<ImmoUpdateComponent>;
    let service: ImmoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [ImmoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ImmoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ImmoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImmoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Immo(123);
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
        const entity = new Immo();
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
