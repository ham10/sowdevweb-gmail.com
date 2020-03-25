import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeFournisDetailComponent } from 'app/entities/type-fournis/type-fournis-detail.component';
import { TypeFournis } from 'app/shared/model/type-fournis.model';

describe('Component Tests', () => {
  describe('TypeFournis Management Detail Component', () => {
    let comp: TypeFournisDetailComponent;
    let fixture: ComponentFixture<TypeFournisDetailComponent>;
    const route = ({ data: of({ typeFournis: new TypeFournis(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeFournisDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeFournisDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeFournisDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeFournis on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeFournis).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
