import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeUniteDetailComponent } from 'app/entities/type-unite/type-unite-detail.component';
import { TypeUnite } from 'app/shared/model/type-unite.model';

describe('Component Tests', () => {
  describe('TypeUnite Management Detail Component', () => {
    let comp: TypeUniteDetailComponent;
    let fixture: ComponentFixture<TypeUniteDetailComponent>;
    const route = ({ data: of({ typeUnite: new TypeUnite(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeUniteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeUniteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeUniteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeUnite on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeUnite).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
