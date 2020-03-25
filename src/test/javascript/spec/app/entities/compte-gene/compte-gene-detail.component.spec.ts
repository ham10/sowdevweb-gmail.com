import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HpdTestModule } from '../../../test.module';
import { CompteGeneDetailComponent } from 'app/entities/compte-gene/compte-gene-detail.component';
import { CompteGene } from 'app/shared/model/compte-gene.model';

describe('Component Tests', () => {
  describe('CompteGene Management Detail Component', () => {
    let comp: CompteGeneDetailComponent;
    let fixture: ComponentFixture<CompteGeneDetailComponent>;
    const route = ({ data: of({ compteGene: new CompteGene(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HpdTestModule],
        declarations: [CompteGeneDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CompteGeneDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompteGeneDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load compteGene on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.compteGene).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
