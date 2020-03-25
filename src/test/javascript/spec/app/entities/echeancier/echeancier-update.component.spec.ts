import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { EcheancierUpdateComponent } from 'app/entities/echeancier/echeancier-update.component';
import { EcheancierService } from 'app/entities/echeancier/echeancier.service';
import { Echeancier } from 'app/shared/model/echeancier.model';

describe('Component Tests', () => {
  describe('Echeancier Management Update Component', () => {
    let comp: EcheancierUpdateComponent;
    let fixture: ComponentFixture<EcheancierUpdateComponent>;
    let service: EcheancierService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [EcheancierUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EcheancierUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EcheancierUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EcheancierService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Echeancier(123);
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
        const entity = new Echeancier();
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
