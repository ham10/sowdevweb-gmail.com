import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { BonDeCommandeService } from 'app/entities/bon-de-commande/bon-de-commande.service';
import { IBonDeCommande, BonDeCommande } from 'app/shared/model/bon-de-commande.model';

describe('Service Tests', () => {
  describe('BonDeCommande Service', () => {
    let injector: TestBed;
    let service: BonDeCommandeService;
    let httpMock: HttpTestingController;
    let elemDefault: IBonDeCommande;
    let expectedResult: IBonDeCommande | IBonDeCommande[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BonDeCommandeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new BonDeCommande(0, 'AAAAAAA', 'AAAAAAA', 0, currentDate, currentDate, currentDate, currentDate, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateComm: currentDate.format(DATE_FORMAT),
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

      it('should create a BonDeCommande', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateComm: currentDate.format(DATE_FORMAT),
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT),
            dateDeleted: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateComm: currentDate,
            dateCreated: currentDate,
            dateUpdated: currentDate,
            dateDeleted: currentDate
          },
          returnedFromService
        );

        service.create(new BonDeCommande()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BonDeCommande', () => {
        const returnedFromService = Object.assign(
          {
            numero: 'BBBBBB',
            libelle: 'BBBBBB',
            prixTotal: 1,
            dateComm: currentDate.format(DATE_FORMAT),
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
            dateComm: currentDate,
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

      it('should return a list of BonDeCommande', () => {
        const returnedFromService = Object.assign(
          {
            numero: 'BBBBBB',
            libelle: 'BBBBBB',
            prixTotal: 1,
            dateComm: currentDate.format(DATE_FORMAT),
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
            dateComm: currentDate,
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

      it('should delete a BonDeCommande', () => {
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
