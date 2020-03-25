import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { JourFerieUpdateComponent } from 'app/entities/jour-ferie/jour-ferie-update.component';
import { JourFerieService } from 'app/entities/jour-ferie/jour-ferie.service';
import { JourFerie } from 'app/shared/model/jour-ferie.model';

describe('Component Tests', () => {
  describe('JourFerie Management Update Component', () => {
    let comp: JourFerieUpdateComponent;
    let fixture: ComponentFixture<JourFerieUpdateComponent>;
    let service: JourFerieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [JourFerieUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(JourFerieUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JourFerieUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JourFerieService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new JourFerie(123);
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
        const entity = new JourFerie();
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
