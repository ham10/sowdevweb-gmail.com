import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { EtatImmoUpdateComponent } from 'app/entities/etat-immo/etat-immo-update.component';
import { EtatImmoService } from 'app/entities/etat-immo/etat-immo.service';
import { EtatImmo } from 'app/shared/model/etat-immo.model';

describe('Component Tests', () => {
  describe('EtatImmo Management Update Component', () => {
    let comp: EtatImmoUpdateComponent;
    let fixture: ComponentFixture<EtatImmoUpdateComponent>;
    let service: EtatImmoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [EtatImmoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EtatImmoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EtatImmoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EtatImmoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EtatImmo(123);
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
        const entity = new EtatImmo();
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
