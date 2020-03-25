import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeFactDetailComponent } from 'app/entities/type-fact/type-fact-detail.component';
import { TypeFact } from 'app/shared/model/type-fact.model';

describe('Component Tests', () => {
  describe('TypeFact Management Detail Component', () => {
    let comp: TypeFactDetailComponent;
    let fixture: ComponentFixture<TypeFactDetailComponent>;
    const route = ({ data: of({ typeFact: new TypeFact(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeFactDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeFactDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeFactDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeFact on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeFact).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
