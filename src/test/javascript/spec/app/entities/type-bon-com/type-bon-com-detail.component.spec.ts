import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeBonComDetailComponent } from 'app/entities/type-bon-com/type-bon-com-detail.component';
import { TypeBonCom } from 'app/shared/model/type-bon-com.model';

describe('Component Tests', () => {
  describe('TypeBonCom Management Detail Component', () => {
    let comp: TypeBonComDetailComponent;
    let fixture: ComponentFixture<TypeBonComDetailComponent>;
    const route = ({ data: of({ typeBonCom: new TypeBonCom(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeBonComDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeBonComDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeBonComDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeBonCom on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeBonCom).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
