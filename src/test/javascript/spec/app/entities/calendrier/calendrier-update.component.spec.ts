import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { CalendrierUpdateComponent } from 'app/entities/calendrier/calendrier-update.component';
import { CalendrierService } from 'app/entities/calendrier/calendrier.service';
import { Calendrier } from 'app/shared/model/calendrier.model';

describe('Component Tests', () => {
  describe('Calendrier Management Update Component', () => {
    let comp: CalendrierUpdateComponent;
    let fixture: ComponentFixture<CalendrierUpdateComponent>;
    let service: CalendrierService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [CalendrierUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CalendrierUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CalendrierUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CalendrierService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Calendrier(123);
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
        const entity = new Calendrier();
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
