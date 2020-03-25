import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { ClasseProdDetailComponent } from 'app/entities/classe-prod/classe-prod-detail.component';
import { ClasseProd } from 'app/shared/model/classe-prod.model';

describe('Component Tests', () => {
  describe('ClasseProd Management Detail Component', () => {
    let comp: ClasseProdDetailComponent;
    let fixture: ComponentFixture<ClasseProdDetailComponent>;
    const route = ({ data: of({ classeProd: new ClasseProd(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [ClasseProdDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ClasseProdDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClasseProdDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load classeProd on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.classeProd).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
