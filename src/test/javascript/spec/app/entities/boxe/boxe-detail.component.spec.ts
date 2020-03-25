import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { BoxeDetailComponent } from 'app/entities/boxe/boxe-detail.component';
import { Boxe } from 'app/shared/model/boxe.model';

describe('Component Tests', () => {
  describe('Boxe Management Detail Component', () => {
    let comp: BoxeDetailComponent;
    let fixture: ComponentFixture<BoxeDetailComponent>;
    const route = ({ data: of({ boxe: new Boxe(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [BoxeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BoxeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BoxeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load boxe on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.boxe).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
