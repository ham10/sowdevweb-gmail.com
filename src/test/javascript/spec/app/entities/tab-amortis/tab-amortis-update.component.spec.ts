import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TabAmortisUpdateComponent } from 'app/entities/tab-amortis/tab-amortis-update.component';
import { TabAmortisService } from 'app/entities/tab-amortis/tab-amortis.service';
import { TabAmortis } from 'app/shared/model/tab-amortis.model';

describe('Component Tests', () => {
  describe('TabAmortis Management Update Component', () => {
    let comp: TabAmortisUpdateComponent;
    let fixture: ComponentFixture<TabAmortisUpdateComponent>;
    let service: TabAmortisService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TabAmortisUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TabAmortisUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TabAmortisUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TabAmortisService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TabAmortis(123);
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
        const entity = new TabAmortis();
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
