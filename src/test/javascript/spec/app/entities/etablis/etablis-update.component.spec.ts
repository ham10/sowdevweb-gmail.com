import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { EtablisUpdateComponent } from 'app/entities/etablis/etablis-update.component';
import { EtablisService } from 'app/entities/etablis/etablis.service';
import { Etablis } from 'app/shared/model/etablis.model';

describe('Component Tests', () => {
  describe('Etablis Management Update Component', () => {
    let comp: EtablisUpdateComponent;
    let fixture: ComponentFixture<EtablisUpdateComponent>;
    let service: EtablisService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [EtablisUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EtablisUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EtablisUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EtablisService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Etablis(123);
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
        const entity = new Etablis();
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
