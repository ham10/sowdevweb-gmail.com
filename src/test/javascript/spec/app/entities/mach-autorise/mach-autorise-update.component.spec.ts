import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { MachAutoriseUpdateComponent } from 'app/entities/mach-autorise/mach-autorise-update.component';
import { MachAutoriseService } from 'app/entities/mach-autorise/mach-autorise.service';
import { MachAutorise } from 'app/shared/model/mach-autorise.model';

describe('Component Tests', () => {
  describe('MachAutorise Management Update Component', () => {
    let comp: MachAutoriseUpdateComponent;
    let fixture: ComponentFixture<MachAutoriseUpdateComponent>;
    let service: MachAutoriseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [MachAutoriseUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MachAutoriseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MachAutoriseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MachAutoriseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MachAutorise(123);
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
        const entity = new MachAutorise();
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
