import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { BonDeCommandeUpdateComponent } from 'app/entities/bon-de-commande/bon-de-commande-update.component';
import { BonDeCommandeService } from 'app/entities/bon-de-commande/bon-de-commande.service';
import { BonDeCommande } from 'app/shared/model/bon-de-commande.model';

describe('Component Tests', () => {
  describe('BonDeCommande Management Update Component', () => {
    let comp: BonDeCommandeUpdateComponent;
    let fixture: ComponentFixture<BonDeCommandeUpdateComponent>;
    let service: BonDeCommandeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [BonDeCommandeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BonDeCommandeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BonDeCommandeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BonDeCommandeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BonDeCommande(123);
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
        const entity = new BonDeCommande();
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
