import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { AntecedentUpdateComponent } from 'app/entities/antecedent/antecedent-update.component';
import { AntecedentService } from 'app/entities/antecedent/antecedent.service';
import { Antecedent } from 'app/shared/model/antecedent.model';

describe('Component Tests', () => {
  describe('Antecedent Management Update Component', () => {
    let comp: AntecedentUpdateComponent;
    let fixture: ComponentFixture<AntecedentUpdateComponent>;
    let service: AntecedentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [AntecedentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AntecedentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AntecedentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AntecedentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Antecedent(123);
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
        const entity = new Antecedent();
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
