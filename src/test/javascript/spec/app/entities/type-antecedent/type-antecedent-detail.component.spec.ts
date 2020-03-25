import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeAntecedentDetailComponent } from 'app/entities/type-antecedent/type-antecedent-detail.component';
import { TypeAntecedent } from 'app/shared/model/type-antecedent.model';

describe('Component Tests', () => {
  describe('TypeAntecedent Management Detail Component', () => {
    let comp: TypeAntecedentDetailComponent;
    let fixture: ComponentFixture<TypeAntecedentDetailComponent>;
    const route = ({ data: of({ typeAntecedent: new TypeAntecedent(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeAntecedentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeAntecedentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeAntecedentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeAntecedent on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeAntecedent).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
