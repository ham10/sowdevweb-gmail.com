import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { EtatBonComUpdateComponent } from 'app/entities/etat-bon-com/etat-bon-com-update.component';
import { EtatBonComService } from 'app/entities/etat-bon-com/etat-bon-com.service';
import { EtatBonCom } from 'app/shared/model/etat-bon-com.model';

describe('Component Tests', () => {
  describe('EtatBonCom Management Update Component', () => {
    let comp: EtatBonComUpdateComponent;
    let fixture: ComponentFixture<EtatBonComUpdateComponent>;
    let service: EtatBonComService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [EtatBonComUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EtatBonComUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EtatBonComUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EtatBonComService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EtatBonCom(123);
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
        const entity = new EtatBonCom();
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
