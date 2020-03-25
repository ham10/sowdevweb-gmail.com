import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { AntecedentDetailComponent } from 'app/entities/antecedent/antecedent-detail.component';
import { Antecedent } from 'app/shared/model/antecedent.model';

describe('Component Tests', () => {
  describe('Antecedent Management Detail Component', () => {
    let comp: AntecedentDetailComponent;
    let fixture: ComponentFixture<AntecedentDetailComponent>;
    const route = ({ data: of({ antecedent: new Antecedent(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [AntecedentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AntecedentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AntecedentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load antecedent on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.antecedent).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
