import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { EtatCaisseUpdateComponent } from 'app/entities/etat-caisse/etat-caisse-update.component';
import { EtatCaisseService } from 'app/entities/etat-caisse/etat-caisse.service';
import { EtatCaisse } from 'app/shared/model/etat-caisse.model';

describe('Component Tests', () => {
  describe('EtatCaisse Management Update Component', () => {
    let comp: EtatCaisseUpdateComponent;
    let fixture: ComponentFixture<EtatCaisseUpdateComponent>;
    let service: EtatCaisseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [EtatCaisseUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EtatCaisseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EtatCaisseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EtatCaisseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EtatCaisse(123);
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
        const entity = new EtatCaisse();
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
