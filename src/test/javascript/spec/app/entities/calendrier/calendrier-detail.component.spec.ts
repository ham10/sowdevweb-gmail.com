import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { CalendrierDetailComponent } from 'app/entities/calendrier/calendrier-detail.component';
import { Calendrier } from 'app/shared/model/calendrier.model';

describe('Component Tests', () => {
  describe('Calendrier Management Detail Component', () => {
    let comp: CalendrierDetailComponent;
    let fixture: ComponentFixture<CalendrierDetailComponent>;
    const route = ({ data: of({ calendrier: new Calendrier(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [CalendrierDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CalendrierDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CalendrierDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load calendrier on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.calendrier).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
