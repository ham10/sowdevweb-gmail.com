import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeSoinsDetailComponent } from 'app/entities/type-soins/type-soins-detail.component';
import { TypeSoins } from 'app/shared/model/type-soins.model';

describe('Component Tests', () => {
  describe('TypeSoins Management Detail Component', () => {
    let comp: TypeSoinsDetailComponent;
    let fixture: ComponentFixture<TypeSoinsDetailComponent>;
    const route = ({ data: of({ typeSoins: new TypeSoins(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeSoinsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeSoinsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeSoinsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeSoins on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeSoins).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
