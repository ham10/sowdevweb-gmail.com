import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { ImmoDetailComponent } from 'app/entities/immo/immo-detail.component';
import { Immo } from 'app/shared/model/immo.model';

describe('Component Tests', () => {
  describe('Immo Management Detail Component', () => {
    let comp: ImmoDetailComponent;
    let fixture: ComponentFixture<ImmoDetailComponent>;
    const route = ({ data: of({ immo: new Immo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [ImmoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ImmoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ImmoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load immo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.immo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
