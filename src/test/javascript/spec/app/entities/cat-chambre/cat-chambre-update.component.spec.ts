import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { CatChambreUpdateComponent } from 'app/entities/cat-chambre/cat-chambre-update.component';
import { CatChambreService } from 'app/entities/cat-chambre/cat-chambre.service';
import { CatChambre } from 'app/shared/model/cat-chambre.model';

describe('Component Tests', () => {
  describe('CatChambre Management Update Component', () => {
    let comp: CatChambreUpdateComponent;
    let fixture: ComponentFixture<CatChambreUpdateComponent>;
    let service: CatChambreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [CatChambreUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CatChambreUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CatChambreUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatChambreService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CatChambre(123);
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
        const entity = new CatChambre();
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
