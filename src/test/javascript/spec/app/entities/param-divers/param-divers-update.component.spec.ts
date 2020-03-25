import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { ParamDiversUpdateComponent } from 'app/entities/param-divers/param-divers-update.component';
import { ParamDiversService } from 'app/entities/param-divers/param-divers.service';
import { ParamDivers } from 'app/shared/model/param-divers.model';

describe('Component Tests', () => {
  describe('ParamDivers Management Update Component', () => {
    let comp: ParamDiversUpdateComponent;
    let fixture: ComponentFixture<ParamDiversUpdateComponent>;
    let service: ParamDiversService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [ParamDiversUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ParamDiversUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParamDiversUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ParamDiversService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ParamDivers(123);
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
        const entity = new ParamDivers();
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
