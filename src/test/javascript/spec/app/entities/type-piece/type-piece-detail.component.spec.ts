import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypePieceDetailComponent } from 'app/entities/type-piece/type-piece-detail.component';
import { TypePiece } from 'app/shared/model/type-piece.model';

describe('Component Tests', () => {
  describe('TypePiece Management Detail Component', () => {
    let comp: TypePieceDetailComponent;
    let fixture: ComponentFixture<TypePieceDetailComponent>;
    const route = ({ data: of({ typePiece: new TypePiece(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypePieceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypePieceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypePieceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typePiece on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typePiece).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
