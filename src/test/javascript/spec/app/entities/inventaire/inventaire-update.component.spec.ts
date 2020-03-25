import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { InventaireUpdateComponent } from 'app/entities/inventaire/inventaire-update.component';
import { InventaireService } from 'app/entities/inventaire/inventaire.service';
import { Inventaire } from 'app/shared/model/inventaire.model';

describe('Component Tests', () => {
  describe('Inventaire Management Update Component', () => {
    let comp: InventaireUpdateComponent;
    let fixture: ComponentFixture<InventaireUpdateComponent>;
    let service: InventaireService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [InventaireUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InventaireUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InventaireUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventaireService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Inventaire(123);
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
        const entity = new Inventaire();
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
