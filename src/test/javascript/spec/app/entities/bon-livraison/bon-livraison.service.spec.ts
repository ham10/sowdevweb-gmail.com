import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { BonLivraisonService } from 'app/entities/bon-livraison/bon-livraison.service';
import { IBonLivraison, BonLivraison } from 'app/shared/model/bon-livraison.model';

describe('Service Tests', () => {
  describe('BonLivraison Service', () => {
    let injector: TestBed;
    let service: BonLivraisonService;
    let httpMock: HttpTestingController;
    let elemDefault: IBonLivraison;
    let expectedResult: IBonLivraison | IBonLivraison[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BonLivraisonService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new BonLivraison(
        0,
        'AAAAAAA',
        0,
        currentDate,
        0,
        currentDate,
        'AAAAAAA',
        currentDate,
        currentDate,
        currentDate,
        0,
        0,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateBonLivraison: currentDate.format(DATE_FORMAT),
            dateCertif: currentDate.format(DATE_FORMAT),
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

      it('should create a BonLivraison', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateBonLivraison: currentDate.format(DATE_FORMAT),
            dateCertif: currentDate.format(DATE_FORMAT),
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT),
            dateDeleted: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateBonLivraison: currentDate,
            dateCertif: currentDate,
            dateCreated: currentDate,
            dateUpdated: currentDate,
            dateDeleted: currentDate
          },
          returnedFromService
        );

        service.create(new BonLivraison()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BonLivraison', () => {
        const returnedFromService = Object.assign(
          {
            designationBonLivraison: 'BBBBBB',
            prixTotalBonLivraison: 1,
            dateBonLivraison: currentDate.format(DATE_FORMAT),
            userCertified: 1,
            dateCertif: currentDate.format(DATE_FORMAT),
            numCertif: 'BBBBBB',
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
            dateBonLivraison: currentDate,
            dateCertif: currentDate,
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

      it('should return a list of BonLivraison', () => {
        const returnedFromService = Object.assign(
          {
            designationBonLivraison: 'BBBBBB',
            prixTotalBonLivraison: 1,
            dateBonLivraison: currentDate.format(DATE_FORMAT),
            userCertified: 1,
            dateCertif: currentDate.format(DATE_FORMAT),
            numCertif: 'BBBBBB',
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
            dateBonLivraison: currentDate,
            dateCertif: currentDate,
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

      it('should delete a BonLivraison', () => {
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
