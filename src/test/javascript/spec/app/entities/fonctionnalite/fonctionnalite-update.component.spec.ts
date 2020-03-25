import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { FonctionnaliteUpdateComponent } from 'app/entities/fonctionnalite/fonctionnalite-update.component';
import { FonctionnaliteService } from 'app/entities/fonctionnalite/fonctionnalite.service';
import { Fonctionnalite } from 'app/shared/model/fonctionnalite.model';

describe('Component Tests', () => {
  describe('Fonctionnalite Management Update Component', () => {
    let comp: FonctionnaliteUpdateComponent;
    let fixture: ComponentFixture<FonctionnaliteUpdateComponent>;
    let service: FonctionnaliteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [FonctionnaliteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FonctionnaliteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FonctionnaliteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FonctionnaliteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Fonctionnalite(123);
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
        const entity = new Fonctionnalite();
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
