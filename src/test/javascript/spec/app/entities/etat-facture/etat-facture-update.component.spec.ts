import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { EtatFactureUpdateComponent } from 'app/entities/etat-facture/etat-facture-update.component';
import { EtatFactureService } from 'app/entities/etat-facture/etat-facture.service';
import { EtatFacture } from 'app/shared/model/etat-facture.model';

describe('Component Tests', () => {
  describe('EtatFacture Management Update Component', () => {
    let comp: EtatFactureUpdateComponent;
    let fixture: ComponentFixture<EtatFactureUpdateComponent>;
    let service: EtatFactureService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [EtatFactureUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EtatFactureUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EtatFactureUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EtatFactureService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EtatFacture(123);
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
        const entity = new EtatFacture();
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
