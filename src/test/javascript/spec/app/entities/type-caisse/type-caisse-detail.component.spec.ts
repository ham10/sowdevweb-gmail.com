import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeCaisseDetailComponent } from 'app/entities/type-caisse/type-caisse-detail.component';
import { TypeCaisse } from 'app/shared/model/type-caisse.model';

describe('Component Tests', () => {
  describe('TypeCaisse Management Detail Component', () => {
    let comp: TypeCaisseDetailComponent;
    let fixture: ComponentFixture<TypeCaisseDetailComponent>;
    const route = ({ data: of({ typeCaisse: new TypeCaisse(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeCaisseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeCaisseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeCaisseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeCaisse on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeCaisse).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
