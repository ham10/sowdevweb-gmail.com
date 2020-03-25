import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { EtatRdvUpdateComponent } from 'app/entities/etat-rdv/etat-rdv-update.component';
import { EtatRdvService } from 'app/entities/etat-rdv/etat-rdv.service';
import { EtatRdv } from 'app/shared/model/etat-rdv.model';

describe('Component Tests', () => {
  describe('EtatRdv Management Update Component', () => {
    let comp: EtatRdvUpdateComponent;
    let fixture: ComponentFixture<EtatRdvUpdateComponent>;
    let service: EtatRdvService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [EtatRdvUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EtatRdvUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EtatRdvUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EtatRdvService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EtatRdv(123);
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
        const entity = new EtatRdv();
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
