import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PatientService } from 'app/entities/patient/patient.service';
import { IPatient, Patient } from 'app/shared/model/patient.model';

describe('Service Tests', () => {
  describe('Patient Service', () => {
    let injector: TestBed;
    let service: PatientService;
    let httpMock: HttpTestingController;
    let elemDefault: IPatient;
    let expectedResult: IPatient | IPatient[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PatientService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Patient(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        false,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
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

      it('should create a Patient', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateNaissance: currentDate.format(DATE_FORMAT),
            dateValidite: currentDate.format(DATE_FORMAT),
            dateCreated: currentDate.format(DATE_FORMAT),
            dateUpdated: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateNaissance: currentDate,
            dateValidite: currentDate,
            dateCreated: currentDate,
            dateUpdated: currentDate
          },
          returnedFromService
        );

        service.create(new Patient()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Patient', () => {
        const returnedFromService = Object.assign(
          {
            codePatient: 'BBBBBB',
            nomPatient: 'BBBBBB',
            prenom: 'BBBBBB',
            adresse: 'BBBBBB',
            email: 'BBBBBB',
            dateNaissance: currentDate.format(DATE_FORMAT),
            genre: 'BBBBBB',
            photo: 'BBBBBB',
            password: 'BBBBBB',
            telephone: 'BBBBBB',
            numeroPiece: 'BBBBBB',
            codeBarre: 'BBBBBB',
            entreprise: 'BBBBBB',
            ville: 'BBBBBB',
            quartier: 'BBBBBB',
            longitude: 'BBBBBB',
            latitude: 'BBBBBB',
            lieuNaissance: 'BBBBBB',
            fonctionPatient: 'BBBBBB',
            situationSociale: 'BBBBBB',
            solde: 1,
            cartePatient: 'BBBBBB',
            bloque: true,
            dateValidite: currentDate.format(DATE_FORMAT),
            motifBlocage: 'BBBBBB',
            prenomPerePatient: 'BBBBBB',
            nomMerePatient: 'BBBBBB',
            prenomMerePatient: 'BBBBBB',
            motifAdmission: 'BBBBBB',
            personneAContacter: 'BBBBBB',
            adressePersAContacter: 'BBBBBB',
            telPersAContacter: 'BBBBBB',
            lienParentePersAContacter: 'BBBBBB',
            nomAccompagnant: 'BBBBBB',
            prenomAccompagnant: 'BBBBBB',
            telAccompagnant: 'BBBBBB',
            habitudeDeVie: 'BBBBBB',
            physioPathologique: 'BBBBBB',
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

      it('should return a list of Patient', () => {
        const returnedFromService = Object.assign(
          {
            codePatient: 'BBBBBB',
            nomPatient: 'BBBBBB',
            prenom: 'BBBBBB',
            adresse: 'BBBBBB',
            email: 'BBBBBB',
            dateNaissance: currentDate.format(DATE_FORMAT),
            genre: 'BBBBBB',
            photo: 'BBBBBB',
            password: 'BBBBBB',
            telephone: 'BBBBBB',
            numeroPiece: 'BBBBBB',
            codeBarre: 'BBBBBB',
            entreprise: 'BBBBBB',
            ville: 'BBBBBB',
            quartier: 'BBBBBB',
            longitude: 'BBBBBB',
            latitude: 'BBBBBB',
            lieuNaissance: 'BBBBBB',
            fonctionPatient: 'BBBBBB',
            situationSociale: 'BBBBBB',
            solde: 1,
            cartePatient: 'BBBBBB',
            bloque: true,
            dateValidite: currentDate.format(DATE_FORMAT),
            motifBlocage: 'BBBBBB',
            prenomPerePatient: 'BBBBBB',
            nomMerePatient: 'BBBBBB',
            prenomMerePatient: 'BBBBBB',
            motifAdmission: 'BBBBBB',
            personneAContacter: 'BBBBBB',
            adressePersAContacter: 'BBBBBB',
            telPersAContacter: 'BBBBBB',
            lienParentePersAContacter: 'BBBBBB',
            nomAccompagnant: 'BBBBBB',
            prenomAccompagnant: 'BBBBBB',
            telAccompagnant: 'BBBBBB',
            habitudeDeVie: 'BBBBBB',
            physioPathologique: 'BBBBBB',
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

      it('should delete a Patient', () => {
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
