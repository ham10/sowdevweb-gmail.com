import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypePieceUpdateComponent } from 'app/entities/type-piece/type-piece-update.component';
import { TypePieceService } from 'app/entities/type-piece/type-piece.service';
import { TypePiece } from 'app/shared/model/type-piece.model';

describe('Component Tests', () => {
  describe('TypePiece Management Update Component', () => {
    let comp: TypePieceUpdateComponent;
    let fixture: ComponentFixture<TypePieceUpdateComponent>;
    let service: TypePieceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypePieceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypePieceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypePieceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypePieceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypePiece(123);
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
        const entity = new TypePiece();
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
