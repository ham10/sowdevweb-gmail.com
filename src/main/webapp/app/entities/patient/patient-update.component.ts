import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPatient, Patient } from 'app/shared/model/patient.model';
import { PatientService } from './patient.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IGroupeSan } from 'app/shared/model/groupe-san.model';
import { GroupeSanService } from 'app/entities/groupe-san/groupe-san.service';
import { ITypePatient } from 'app/shared/model/type-patient.model';
import { TypePatientService } from 'app/entities/type-patient/type-patient.service';
import { IDepartement } from 'app/shared/model/departement.model';
import { DepartementService } from 'app/entities/departement/departement.service';

type SelectableEntity = IGroupeSan | ITypePatient | IDepartement;

@Component({
  selector: 'jhi-patient-update',
  templateUrl: './patient-update.component.html'
})
export class PatientUpdateComponent implements OnInit {
  isSaving = false;
  groupesans: IGroupeSan[] = [];
  typepatients: ITypePatient[] = [];
  departements: IDepartement[] = [];
  dateNaissanceDp: any;
  dateValiditeDp: any;
  dateCreatedDp: any;
  dateUpdatedDp: any;

  editForm = this.fb.group({
    id: [],
    codePatient: [null, [Validators.required]],
    nomPatient: [null, [Validators.required]],
    prenom: [null, [Validators.required]],
    adresse: [null, [Validators.required]],
    email: [null, [Validators.required]],
    dateNaissance: [null, [Validators.required]],
    genre: [null, [Validators.required]],
    photo: [],
    photoContentType: [],
    password: [],
    telephone: [null, [Validators.required]],
    numeroPiece: [],
    codeBarre: [],
    entreprise: [],
    ville: [],
    quartier: [],
    longitude: [],
    latitude: [],
    lieuNaissance: [],
    fonctionPatient: [],
    situationSociale: [],
    solde: [],
    cartePatient: [],
    bloque: [],
    dateValidite: [],
    motifBlocage: [],
    prenomPerePatient: [],
    nomMerePatient: [],
    prenomMerePatient: [],
    motifAdmission: [],
    personneAContacter: [],
    adressePersAContacter: [],
    telPersAContacter: [],
    lienParentePersAContacter: [],
    nomAccompagnant: [],
    prenomAccompagnant: [],
    telAccompagnant: [],
    habitudeDeVie: [],
    physioPathologique: [],
    dateCreated: [],
    dateUpdated: [],
    userCreated: [],
    userUpdated: [],
    userDeleted: [],
    groupeSanguin: [],
    typePatient: [],
    departement: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected patientService: PatientService,
    protected groupeSanService: GroupeSanService,
    protected typePatientService: TypePatientService,
    protected departementService: DepartementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ patient }) => {
      this.updateForm(patient);

      this.groupeSanService.query().subscribe((res: HttpResponse<IGroupeSan[]>) => (this.groupesans = res.body || []));

      this.typePatientService.query().subscribe((res: HttpResponse<ITypePatient[]>) => (this.typepatients = res.body || []));

      this.departementService.query().subscribe((res: HttpResponse<IDepartement[]>) => (this.departements = res.body || []));
    });
  }

  updateForm(patient: IPatient): void {
    this.editForm.patchValue({
      id: patient.id,
      codePatient: patient.codePatient,
      nomPatient: patient.nomPatient,
      prenom: patient.prenom,
      adresse: patient.adresse,
      email: patient.email,
      dateNaissance: patient.dateNaissance,
      genre: patient.genre,
      photo: patient.photo,
      photoContentType: patient.photoContentType,
      password: patient.password,
      telephone: patient.telephone,
      numeroPiece: patient.numeroPiece,
      codeBarre: patient.codeBarre,
      entreprise: patient.entreprise,
      ville: patient.ville,
      quartier: patient.quartier,
      longitude: patient.longitude,
      latitude: patient.latitude,
      lieuNaissance: patient.lieuNaissance,
      fonctionPatient: patient.fonctionPatient,
      situationSociale: patient.situationSociale,
      solde: patient.solde,
      cartePatient: patient.cartePatient,
      bloque: patient.bloque,
      dateValidite: patient.dateValidite,
      motifBlocage: patient.motifBlocage,
      prenomPerePatient: patient.prenomPerePatient,
      nomMerePatient: patient.nomMerePatient,
      prenomMerePatient: patient.prenomMerePatient,
      motifAdmission: patient.motifAdmission,
      personneAContacter: patient.personneAContacter,
      adressePersAContacter: patient.adressePersAContacter,
      telPersAContacter: patient.telPersAContacter,
      lienParentePersAContacter: patient.lienParentePersAContacter,
      nomAccompagnant: patient.nomAccompagnant,
      prenomAccompagnant: patient.prenomAccompagnant,
      telAccompagnant: patient.telAccompagnant,
      habitudeDeVie: patient.habitudeDeVie,
      physioPathologique: patient.physioPathologique,
      dateCreated: patient.dateCreated,
      dateUpdated: patient.dateUpdated,
      userCreated: patient.userCreated,
      userUpdated: patient.userUpdated,
      userDeleted: patient.userDeleted,
      groupeSanguin: patient.groupeSanguin,
      typePatient: patient.typePatient,
      departement: patient.departement
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('hpdApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const patient = this.createFromForm();
    if (patient.id !== undefined) {
      this.subscribeToSaveResponse(this.patientService.update(patient));
    } else {
      this.subscribeToSaveResponse(this.patientService.create(patient));
    }
  }

  private createFromForm(): IPatient {
    return {
      ...new Patient(),
      id: this.editForm.get(['id'])!.value,
      codePatient: this.editForm.get(['codePatient'])!.value,
      nomPatient: this.editForm.get(['nomPatient'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      email: this.editForm.get(['email'])!.value,
      dateNaissance: this.editForm.get(['dateNaissance'])!.value,
      genre: this.editForm.get(['genre'])!.value,
      photoContentType: this.editForm.get(['photoContentType'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      password: this.editForm.get(['password'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      numeroPiece: this.editForm.get(['numeroPiece'])!.value,
      codeBarre: this.editForm.get(['codeBarre'])!.value,
      entreprise: this.editForm.get(['entreprise'])!.value,
      ville: this.editForm.get(['ville'])!.value,
      quartier: this.editForm.get(['quartier'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
      lieuNaissance: this.editForm.get(['lieuNaissance'])!.value,
      fonctionPatient: this.editForm.get(['fonctionPatient'])!.value,
      situationSociale: this.editForm.get(['situationSociale'])!.value,
      solde: this.editForm.get(['solde'])!.value,
      cartePatient: this.editForm.get(['cartePatient'])!.value,
      bloque: this.editForm.get(['bloque'])!.value,
      dateValidite: this.editForm.get(['dateValidite'])!.value,
      motifBlocage: this.editForm.get(['motifBlocage'])!.value,
      prenomPerePatient: this.editForm.get(['prenomPerePatient'])!.value,
      nomMerePatient: this.editForm.get(['nomMerePatient'])!.value,
      prenomMerePatient: this.editForm.get(['prenomMerePatient'])!.value,
      motifAdmission: this.editForm.get(['motifAdmission'])!.value,
      personneAContacter: this.editForm.get(['personneAContacter'])!.value,
      adressePersAContacter: this.editForm.get(['adressePersAContacter'])!.value,
      telPersAContacter: this.editForm.get(['telPersAContacter'])!.value,
      lienParentePersAContacter: this.editForm.get(['lienParentePersAContacter'])!.value,
      nomAccompagnant: this.editForm.get(['nomAccompagnant'])!.value,
      prenomAccompagnant: this.editForm.get(['prenomAccompagnant'])!.value,
      telAccompagnant: this.editForm.get(['telAccompagnant'])!.value,
      habitudeDeVie: this.editForm.get(['habitudeDeVie'])!.value,
      physioPathologique: this.editForm.get(['physioPathologique'])!.value,
      dateCreated: this.editForm.get(['dateCreated'])!.value,
      dateUpdated: this.editForm.get(['dateUpdated'])!.value,
      userCreated: this.editForm.get(['userCreated'])!.value,
      userUpdated: this.editForm.get(['userUpdated'])!.value,
      userDeleted: this.editForm.get(['userDeleted'])!.value,
      groupeSanguin: this.editForm.get(['groupeSanguin'])!.value,
      typePatient: this.editForm.get(['typePatient'])!.value,
      departement: this.editForm.get(['departement'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPatient>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
