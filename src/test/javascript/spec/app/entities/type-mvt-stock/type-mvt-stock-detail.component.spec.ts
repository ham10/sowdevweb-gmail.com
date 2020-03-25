import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeMvtStockDetailComponent } from 'app/entities/type-mvt-stock/type-mvt-stock-detail.component';
import { TypeMvtStock } from 'app/shared/model/type-mvt-stock.model';

describe('Component Tests', () => {
  describe('TypeMvtStock Management Detail Component', () => {
    let comp: TypeMvtStockDetailComponent;
    let fixture: ComponentFixture<TypeMvtStockDetailComponent>;
    const route = ({ data: of({ typeMvtStock: new TypeMvtStock(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeMvtStockDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeMvtStockDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeMvtStockDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeMvtStock on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeMvtStock).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
