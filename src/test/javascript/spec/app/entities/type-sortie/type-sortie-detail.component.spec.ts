import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeSortieDetailComponent } from 'app/entities/type-sortie/type-sortie-detail.component';
import { TypeSortie } from 'app/shared/model/type-sortie.model';

describe('Component Tests', () => {
  describe('TypeSortie Management Detail Component', () => {
    let comp: TypeSortieDetailComponent;
    let fixture: ComponentFixture<TypeSortieDetailComponent>;
    const route = ({ data: of({ typeSortie: new TypeSortie(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeSortieDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeSortieDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeSortieDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeSortie on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeSortie).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
