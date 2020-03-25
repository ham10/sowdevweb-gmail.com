import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { CompteGeneUpdateComponent } from 'app/entities/compte-gene/compte-gene-update.component';
import { CompteGeneService } from 'app/entities/compte-gene/compte-gene.service';
import { CompteGene } from 'app/shared/model/compte-gene.model';

describe('Component Tests', () => {
  describe('CompteGene Management Update Component', () => {
    let comp: CompteGeneUpdateComponent;
    let fixture: ComponentFixture<CompteGeneUpdateComponent>;
    let service: CompteGeneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [CompteGeneUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CompteGeneUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompteGeneUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompteGeneService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CompteGene(123);
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
        const entity = new CompteGene();
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
