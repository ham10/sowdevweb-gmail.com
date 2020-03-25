import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeProdDetailComponent } from 'app/entities/type-prod/type-prod-detail.component';
import { TypeProd } from 'app/shared/model/type-prod.model';

describe('Component Tests', () => {
  describe('TypeProd Management Detail Component', () => {
    let comp: TypeProdDetailComponent;
    let fixture: ComponentFixture<TypeProdDetailComponent>;
    const route = ({ data: of({ typeProd: new TypeProd(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeProdDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeProdDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeProdDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeProd on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeProd).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
