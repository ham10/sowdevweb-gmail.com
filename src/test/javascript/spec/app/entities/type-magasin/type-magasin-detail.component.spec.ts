import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeMagasinDetailComponent } from 'app/entities/type-magasin/type-magasin-detail.component';
import { TypeMagasin } from 'app/shared/model/type-magasin.model';

describe('Component Tests', () => {
  describe('TypeMagasin Management Detail Component', () => {
    let comp: TypeMagasinDetailComponent;
    let fixture: ComponentFixture<TypeMagasinDetailComponent>;
    const route = ({ data: of({ typeMagasin: new TypeMagasin(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeMagasinDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeMagasinDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeMagasinDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeMagasin on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeMagasin).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
