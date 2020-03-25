import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { TabAmortisDetailComponent } from 'app/entities/tab-amortis/tab-amortis-detail.component';
import { TabAmortis } from 'app/shared/model/tab-amortis.model';

describe('Component Tests', () => {
  describe('TabAmortis Management Detail Component', () => {
    let comp: TabAmortisDetailComponent;
    let fixture: ComponentFixture<TabAmortisDetailComponent>;
    const route = ({ data: of({ tabAmortis: new TabAmortis(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [TabAmortisDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TabAmortisDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TabAmortisDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tabAmortis on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tabAmortis).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
