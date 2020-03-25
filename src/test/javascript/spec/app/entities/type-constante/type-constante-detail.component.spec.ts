import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeConstanteDetailComponent } from 'app/entities/type-constante/type-constante-detail.component';
import { TypeConstante } from 'app/shared/model/type-constante.model';

describe('Component Tests', () => {
  describe('TypeConstante Management Detail Component', () => {
    let comp: TypeConstanteDetailComponent;
    let fixture: ComponentFixture<TypeConstanteDetailComponent>;
    const route = ({ data: of({ typeConstante: new TypeConstante(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeConstanteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeConstanteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeConstanteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeConstante on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeConstante).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
