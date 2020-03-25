import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { SchemaComptaUpdateComponent } from 'app/entities/schema-compta/schema-compta-update.component';
import { SchemaComptaService } from 'app/entities/schema-compta/schema-compta.service';
import { SchemaCompta } from 'app/shared/model/schema-compta.model';

describe('Component Tests', () => {
  describe('SchemaCompta Management Update Component', () => {
    let comp: SchemaComptaUpdateComponent;
    let fixture: ComponentFixture<SchemaComptaUpdateComponent>;
    let service: SchemaComptaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [SchemaComptaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SchemaComptaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SchemaComptaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SchemaComptaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SchemaCompta(123);
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
        const entity = new SchemaCompta();
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
