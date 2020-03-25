import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypePlateauDetailComponent } from 'app/entities/type-plateau/type-plateau-detail.component';
import { TypePlateau } from 'app/shared/model/type-plateau.model';

describe('Component Tests', () => {
  describe('TypePlateau Management Detail Component', () => {
    let comp: TypePlateauDetailComponent;
    let fixture: ComponentFixture<TypePlateauDetailComponent>;
    const route = ({ data: of({ typePlateau: new TypePlateau(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypePlateauDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypePlateauDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypePlateauDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typePlateau on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typePlateau).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
