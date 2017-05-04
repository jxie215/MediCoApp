package com.example.jaden.medicoapp.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jaden.medicoapp.LoginActivity;
import com.example.jaden.medicoapp.R;
import com.example.jaden.medicoapp.doctor.appointment.AppointmentFragment;
import com.example.jaden.medicoapp.doctor.home.DoctorHomeFragment;
import com.example.jaden.medicoapp.doctor.request.RequestFragment;
import com.example.jaden.medicoapp.doctor.workplan.WorkPlanFragment;

public class DoctorMainActivity extends AppCompatActivity {
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main);
        if(findViewById(R.id.doctor_main_container) != null) {
            DoctorHomeFragment sellerHomeFragment = new DoctorHomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.doctor_main_container, sellerHomeFragment).commit();
        }
        mBottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_nav);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        DoctorHomeFragment doctorHomeFragment = new DoctorHomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.doctor_main_container, doctorHomeFragment).commit();
                        break;
                    case R.id.menu_request:
                        RequestFragment requestFragment = new RequestFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.doctor_main_container, requestFragment).commit();
                        break;
                    case R.id.menu_appointment:
                        AppointmentFragment appointmentRecordFragment = new AppointmentFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.doctor_main_container, appointmentRecordFragment).commit();
                        break;
                    case R.id.menu_plan:
                        WorkPlanFragment workPlanFragment = new WorkPlanFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.doctor_main_container, workPlanFragment).commit();
                        break;
                }
                return true;
            }
        });
    }
    boolean adder = true;
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (adder){
            menu.add("Log out");
            adder = false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent signOutIntent = new Intent(this, LoginActivity.class);
        Toast.makeText(this,"Logged Out",Toast.LENGTH_SHORT).show();
        startActivity(signOutIntent);
        finish();
        return super.onOptionsItemSelected(item);
    }
}
