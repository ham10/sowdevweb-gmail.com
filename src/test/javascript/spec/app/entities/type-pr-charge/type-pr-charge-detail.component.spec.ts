import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypePrChargeDetailComponent } from 'app/entities/type-pr-charge/type-pr-charge-detail.component';
import { TypePrCharge } from 'app/shared/model/type-pr-charge.model';

describe('Component Tests', () => {
  describe('TypePrCharge Management Detail Component', () => {
    let comp: TypePrChargeDetailComponent;
    let fixture: ComponentFixture<TypePrChargeDetailComponent>;
    const route = ({ data: of({ typePrCharge: new TypePrCharge(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypePrChargeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypePrChargeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypePrChargeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typePrCharge on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typePrCharge).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
