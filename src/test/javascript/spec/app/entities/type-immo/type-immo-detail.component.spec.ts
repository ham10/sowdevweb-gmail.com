import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeImmoDetailComponent } from 'app/entities/type-immo/type-immo-detail.component';
import { TypeImmo } from 'app/shared/model/type-immo.model';

describe('Component Tests', () => {
  describe('TypeImmo Management Detail Component', () => {
    let comp: TypeImmoDetailComponent;
    let fixture: ComponentFixture<TypeImmoDetailComponent>;
    const route = ({ data: of({ typeImmo: new TypeImmo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeImmoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeImmoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeImmoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeImmo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeImmo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
