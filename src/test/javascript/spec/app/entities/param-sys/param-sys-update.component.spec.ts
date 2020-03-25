import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { ParamSysUpdateComponent } from 'app/entities/param-sys/param-sys-update.component';
import { ParamSysService } from 'app/entities/param-sys/param-sys.service';
import { ParamSys } from 'app/shared/model/param-sys.model';

describe('Component Tests', () => {
  describe('ParamSys Management Update Component', () => {
    let comp: ParamSysUpdateComponent;
    let fixture: ComponentFixture<ParamSysUpdateComponent>;
    let service: ParamSysService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [ParamSysUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ParamSysUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParamSysUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ParamSysService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ParamSys(123);
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
        const entity = new ParamSys();
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
