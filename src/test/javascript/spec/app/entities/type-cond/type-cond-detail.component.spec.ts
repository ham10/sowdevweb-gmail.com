import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeCondDetailComponent } from 'app/entities/type-cond/type-cond-detail.component';
import { TypeCond } from 'app/shared/model/type-cond.model';

describe('Component Tests', () => {
  describe('TypeCond Management Detail Component', () => {
    let comp: TypeCondDetailComponent;
    let fixture: ComponentFixture<TypeCondDetailComponent>;
    const route = ({ data: of({ typeCond: new TypeCond(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeCondDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeCondDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeCondDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeCond on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeCond).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
