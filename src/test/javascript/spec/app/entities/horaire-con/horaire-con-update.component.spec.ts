import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { HoraireConUpdateComponent } from 'app/entities/horaire-con/horaire-con-update.component';
import { HoraireConService } from 'app/entities/horaire-con/horaire-con.service';
import { HoraireCon } from 'app/shared/model/horaire-con.model';

describe('Component Tests', () => {
  describe('HoraireCon Management Update Component', () => {
    let comp: HoraireConUpdateComponent;
    let fixture: ComponentFixture<HoraireConUpdateComponent>;
    let service: HoraireConService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [HoraireConUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(HoraireConUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HoraireConUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HoraireConService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new HoraireCon(123);
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
        const entity = new HoraireCon();
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
