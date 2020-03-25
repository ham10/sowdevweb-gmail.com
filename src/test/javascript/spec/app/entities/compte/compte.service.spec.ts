import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CompteService } from 'app/entities/compte/compte.service';
import { ICompte, Compte } from 'app/shared/model/compte.model';

describe('Service Tests', () => {
  describe('Compte Service', () => {
    let injector: TestBed;
    let service: CompteService;
    let httpMock: HttpTestingController;
    let elemDefault: ICompte;
    let expectedResult: ICompte | ICompte[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CompteService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Compte(0, 0, 'AAAAAAA', 0, 'AAAAAAA', 0, 0, currentDate, currentDate, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Compte', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreated: currentDate,
            dateUpdated: currentDate
          },
          returnedFromService
        );

        service.create(new Compte()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Compte', () => {
        const returnedFromService = Object.assign(
          {
            numeroCompte: 1,
            libelleCompte: 'BBBBBB',
            soldeCompte: 1,
            sensCompte: 'BBBBBB',
            cumulMouvDebit: 1,
            cumulMouvCredit: 1,
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT),
            dateDeleted: 1,
            userCreated: 1,
            userUpdated: 1,
            userDeleted: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreated: currentDate,
            dateUpdated: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Compte', () => {
        const returnedFromService = Object.assign(
          {
            numeroCompte: 1,
            libelleCompte: 'BBBBBB',
            soldeCompte: 1,
            sensCompte: 'BBBBBB',
            cumulMouvDebit: 1,
            cumulMouvCredit: 1,
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT),
            dateDeleted: 1,
            userCreated: 1,
            userUpdated: 1,
            userDeleted: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreated: currentDate,
            dateUpdated: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Compte', () => {
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
