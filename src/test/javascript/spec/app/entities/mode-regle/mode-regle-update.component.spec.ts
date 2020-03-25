import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { ModeRegleUpdateComponent } from 'app/entities/mode-regle/mode-regle-update.component';
import { ModeRegleService } from 'app/entities/mode-regle/mode-regle.service';
import { ModeRegle } from 'app/shared/model/mode-regle.model';

describe('Component Tests', () => {
  describe('ModeRegle Management Update Component', () => {
    let comp: ModeRegleUpdateComponent;
    let fixture: ComponentFixture<ModeRegleUpdateComponent>;
    let service: ModeRegleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [ModeRegleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ModeRegleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ModeRegleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ModeRegleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ModeRegle(123);
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
        const entity = new ModeRegle();
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
