import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { FormeProdDetailComponent } from 'app/entities/forme-prod/forme-prod-detail.component';
import { FormeProd } from 'app/shared/model/forme-prod.model';

describe('Component Tests', () => {
  describe('FormeProd Management Detail Component', () => {
    let comp: FormeProdDetailComponent;
    let fixture: ComponentFixture<FormeProdDetailComponent>;
    const route = ({ data: of({ formeProd: new FormeProd(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [FormeProdDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FormeProdDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FormeProdDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load formeProd on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.formeProd).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
