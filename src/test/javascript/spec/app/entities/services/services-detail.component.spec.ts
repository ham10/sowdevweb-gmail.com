import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { ServicesDetailComponent } from 'app/entities/services/services-detail.component';
import { Services } from 'app/shared/model/services.model';

describe('Component Tests', () => {
  describe('Services Management Detail Component', () => {
    let comp: ServicesDetailComponent;
    let fixture: ComponentFixture<ServicesDetailComponent>;
    const route = ({ data: of({ services: new Services(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [ServicesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServicesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServicesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load services on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.services).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
