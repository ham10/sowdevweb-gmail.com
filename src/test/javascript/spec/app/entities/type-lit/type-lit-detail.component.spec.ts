import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeLitDetailComponent } from 'app/entities/type-lit/type-lit-detail.component';
import { TypeLit } from 'app/shared/model/type-lit.model';

describe('Component Tests', () => {
  describe('TypeLit Management Detail Component', () => {
    let comp: TypeLitDetailComponent;
    let fixture: ComponentFixture<TypeLitDetailComponent>;
    const route = ({ data: of({ typeLit: new TypeLit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeLitDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeLitDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeLitDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeLit on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeLit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
