package com.example.jaden.medicoapp.doctor.utils;

import java.util.ArrayList;

public class ConfirmedList extends ArrayList<AppointmentValues> {
    private static ConfirmedList ourInstance = null;

    public static ConfirmedList getInstance() {
        if (ourInstance == null) {
            ourInstance = new ConfirmedList();
        }
        return ourInstance;
    }

    private ConfirmedList() {
    }
}
