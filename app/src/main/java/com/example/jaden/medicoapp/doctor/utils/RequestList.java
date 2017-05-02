package com.example.jaden.medicoapp.doctor.utils;

import java.util.ArrayList;

public class RequestList extends ArrayList<AppointmentValues>{
    private static RequestList ourInstance = null;

    public static RequestList getInstance() {
        if (ourInstance == null) {
            ourInstance = new RequestList();
        }
        return ourInstance;
    }

    private RequestList() {
    }
}
