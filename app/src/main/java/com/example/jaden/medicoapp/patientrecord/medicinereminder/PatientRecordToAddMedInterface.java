package com.example.jaden.medicoapp.patientrecord.medicinereminder;

/**
 * Created by snehalsutar on 2/16/16.
 */
public interface PatientRecordToAddMedInterface {
    void patientRecToAddMedCommunication(String mJsonArray);

    void modifyExistingAlarm(String mJsonArray, int position);
}