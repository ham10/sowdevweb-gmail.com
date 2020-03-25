import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TypeServicesDetailComponent } from 'app/entities/type-services/type-services-detail.component';
import { TypeServices } from 'app/shared/model/type-services.model';

describe('Component Tests', () => {
  describe('TypeServices Management Detail Component', () => {
    let comp: TypeServicesDetailComponent;
    let fixture: ComponentFixture<TypeServicesDetailComponent>;
    const route = ({ data: of({ typeServices: new TypeServices(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TypeServicesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeServicesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeServicesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeServices on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeServices).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
