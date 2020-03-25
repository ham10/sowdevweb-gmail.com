import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeTarifUpdateComponent } from 'app/entities/type-tarif/type-tarif-update.component';
import { TypeTarifService } from 'app/entities/type-tarif/type-tarif.service';
import { TypeTarif } from 'app/shared/model/type-tarif.model';

describe('Component Tests', () => {
  describe('TypeTarif Management Update Component', () => {
    let comp: TypeTarifUpdateComponent;
    let fixture: ComponentFixture<TypeTarifUpdateComponent>;
    let service: TypeTarifService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeTarifUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeTarifUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeTarifUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeTarifService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeTarif(123);
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
        const entity = new TypeTarif();
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
