import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { LigneLivraisonUpdateComponent } from 'app/entities/ligne-livraison/ligne-livraison-update.component';
import { LigneLivraisonService } from 'app/entities/ligne-livraison/ligne-livraison.service';
import { LigneLivraison } from 'app/shared/model/ligne-livraison.model';

describe('Component Tests', () => {
  describe('LigneLivraison Management Update Component', () => {
    let comp: LigneLivraisonUpdateComponent;
    let fixture: ComponentFixture<LigneLivraisonUpdateComponent>;
    let service: LigneLivraisonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [LigneLivraisonUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LigneLivraisonUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LigneLivraisonUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LigneLivraisonService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LigneLivraison(123);
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
        const entity = new LigneLivraison();
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
