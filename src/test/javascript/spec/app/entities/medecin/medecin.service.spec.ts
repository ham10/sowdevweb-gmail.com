import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { MedecinService } from 'app/entities/medecin/medecin.service';
import { IMedecin, Medecin } from 'app/shared/model/medecin.model';

describe('Service Tests', () => {
  describe('Medecin Service', () => {
    let injector: TestBed;
    let service: MedecinService;
    let httpMock: HttpTestingController;
    let elemDefault: IMedecin;
    let expectedResult: IMedecin | IMedecin[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MedecinService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Medecin(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
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
            dateNaissance: currentDate.format(DATE_FORMAT),
            dateEmbauche: currentDate.format(DATE_FORMAT),
            dateValidite: currentDate.format(DATE_FORMAT),
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

      it('should create a Medecin', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateNaissance: currentDate.format(DATE_FORMAT),
            dateEmbauche: currentDate.format(DATE_FORMAT),
            dateValidite: currentDate.format(DATE_FORMAT),
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateNaissance: currentDate,
            dateEmbauche: currentDate,
            dateValidite: currentDate,
            dateCreated: currentDate,
            dateUpdated: currentDate
          },
          returnedFromService
        );

        service.create(new Medecin()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Medecin', () => {
        const returnedFromService = Object.assign(
          {
            nomMedecin: 'BBBBBB',
            prenom: 'BBBBBB',
            adresse: 'BBBBBB',
            email: 'BBBBBB',
            dateNaissance: currentDate.format(DATE_FORMAT),
            genreMedecin: 'BBBBBB',
            nationalite: 'BBBBBB',
            telephone: 'BBBBBB',
            anciennete: 1,
            numeroPiece: 1,
            gradeMedecin: 'BBBBBB',
            specialite: 'BBBBBB',
            dateEmbauche: currentDate.format(DATE_FORMAT),
            dateValidite: currentDate.format(DATE_FORMAT),
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT),
            userCreated: 1,
            userUpdated: 1,
            userDeleted: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateNaissance: currentDate,
            dateEmbauche: currentDate,
            dateValidite: currentDate,
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

      it('should return a list of Medecin', () => {
        const returnedFromService = Object.assign(
          {
            nomMedecin: 'BBBBBB',
            prenom: 'BBBBBB',
            adresse: 'BBBBBB',
            email: 'BBBBBB',
            dateNaissance: currentDate.format(DATE_FORMAT),
            genreMedecin: 'BBBBBB',
            nationalite: 'BBBBBB',
            telephone: 'BBBBBB',
            anciennete: 1,
            numeroPiece: 1,
            gradeMedecin: 'BBBBBB',
            specialite: 'BBBBBB',
            dateEmbauche: currentDate.format(DATE_FORMAT),
            dateValidite: currentDate.format(DATE_FORMAT),
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT),
            userCreated: 1,
            userUpdated: 1,
            userDeleted: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateNaissance: currentDate,
            dateEmbauche: currentDate,
            dateValidite: currentDate,
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

      it('should delete a Medecin', () => {
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
