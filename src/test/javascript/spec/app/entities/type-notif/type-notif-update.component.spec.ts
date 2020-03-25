import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeNotifUpdateComponent } from 'app/entities/type-notif/type-notif-update.component';
import { TypeNotifService } from 'app/entities/type-notif/type-notif.service';
import { TypeNotif } from 'app/shared/model/type-notif.model';

describe('Component Tests', () => {
  describe('TypeNotif Management Update Component', () => {
    let comp: TypeNotifUpdateComponent;
    let fixture: ComponentFixture<TypeNotifUpdateComponent>;
    let service: TypeNotifService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeNotifUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeNotifUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeNotifUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeNotifService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeNotif(123);
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
        const entity = new TypeNotif();
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
