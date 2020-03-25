import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { GroupeSanUpdateComponent } from 'app/entities/groupe-san/groupe-san-update.component';
import { GroupeSanService } from 'app/entities/groupe-san/groupe-san.service';
import { GroupeSan } from 'app/shared/model/groupe-san.model';

describe('Component Tests', () => {
  describe('GroupeSan Management Update Component', () => {
    let comp: GroupeSanUpdateComponent;
    let fixture: ComponentFixture<GroupeSanUpdateComponent>;
    let service: GroupeSanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [GroupeSanUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GroupeSanUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GroupeSanUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GroupeSanService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GroupeSan(123);
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
        const entity = new GroupeSan();
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
