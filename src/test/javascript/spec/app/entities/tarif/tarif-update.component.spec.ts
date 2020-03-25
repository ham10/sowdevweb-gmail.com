import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TarifUpdateComponent } from 'app/entities/tarif/tarif-update.component';
import { TarifService } from 'app/entities/tarif/tarif.service';
import { Tarif } from 'app/shared/model/tarif.model';

describe('Component Tests', () => {
  describe('Tarif Management Update Component', () => {
    let comp: TarifUpdateComponent;
    let fixture: ComponentFixture<TarifUpdateComponent>;
    let service: TarifService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TarifUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TarifUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TarifUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TarifService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Tarif(123);
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
        const entity = new Tarif();
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
