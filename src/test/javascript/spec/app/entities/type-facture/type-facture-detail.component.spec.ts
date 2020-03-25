import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeFactureDetailComponent } from 'app/entities/type-facture/type-facture-detail.component';
import { TypeFacture } from 'app/shared/model/type-facture.model';

describe('Component Tests', () => {
  describe('TypeFacture Management Detail Component', () => {
    let comp: TypeFactureDetailComponent;
    let fixture: ComponentFixture<TypeFactureDetailComponent>;
    const route = ({ data: of({ typeFacture: new TypeFacture(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeFactureDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeFactureDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeFactureDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeFacture on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeFacture).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
