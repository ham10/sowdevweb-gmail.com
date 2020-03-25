import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeChampsDetailComponent } from 'app/entities/type-champs/type-champs-detail.component';
import { TypeChamps } from 'app/shared/model/type-champs.model';

describe('Component Tests', () => {
  describe('TypeChamps Management Detail Component', () => {
    let comp: TypeChampsDetailComponent;
    let fixture: ComponentFixture<TypeChampsDetailComponent>;
    const route = ({ data: of({ typeChamps: new TypeChamps(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeChampsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeChampsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeChampsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeChamps on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeChamps).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
