import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypePoleDetailComponent } from 'app/entities/type-pole/type-pole-detail.component';
import { TypePole } from 'app/shared/model/type-pole.model';

describe('Component Tests', () => {
  describe('TypePole Management Detail Component', () => {
    let comp: TypePoleDetailComponent;
    let fixture: ComponentFixture<TypePoleDetailComponent>;
    const route = ({ data: of({ typePole: new TypePole(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypePoleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypePoleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypePoleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typePole on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typePole).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
