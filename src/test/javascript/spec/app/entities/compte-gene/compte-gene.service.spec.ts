import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CompteGeneService } from 'app/entities/compte-gene/compte-gene.service';
import { ICompteGene, CompteGene } from 'app/shared/model/compte-gene.model';

describe('Service Tests', () => {
  describe('CompteGene Service', () => {
    let injector: TestBed;
    let service: CompteGeneService;
    let httpMock: HttpTestingController;
    let elemDefault: ICompteGene;
    let expectedResult: ICompteGene | ICompteGene[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CompteGeneService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CompteGene(0, 0, 'AAAAAAA', 'AAAAAAA', 0, 0, 0, currentDate, currentDate, currentDate, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT),
            dateDeleted: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CompteGene', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT),
            dateDeleted: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreated: currentDate,
            dateUpdated: currentDate,
            dateDeleted: currentDate
          },
          returnedFromService
        );

        service.create(new CompteGene()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CompteGene', () => {
        const returnedFromService = Object.assign(
          {
            numeroCompteGene: 1,
            libelleCompteGene: 'BBBBBB',
            sensCompteGene: 'BBBBBB',
            soldeCompteGene: 1,
            cumulMouvDebitCompteGene: 1,
            cumulMouvCreditCompteGene: 1,
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT),
            dateDeleted: currentDate.format(DATE_FORMAT),
            userCreated: 1,
            userUpdated: 1,
            userDeleted: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreated: currentDate,
            dateUpdated: currentDate,
            dateDeleted: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CompteGene', () => {
        const returnedFromService = Object.assign(
          {
            numeroCompteGene: 1,
            libelleCompteGene: 'BBBBBB',
            sensCompteGene: 'BBBBBB',
            soldeCompteGene: 1,
            cumulMouvDebitCompteGene: 1,
            cumulMouvCreditCompteGene: 1,
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT),
            dateDeleted: currentDate.format(DATE_FORMAT),
            userCreated: 1,
            userUpdated: 1,
            userDeleted: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreated: currentDate,
            dateUpdated: currentDate,
            dateDeleted: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CompteGene', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
