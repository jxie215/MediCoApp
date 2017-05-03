package com.example.jaden.medicoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.jaden.medicoapp.doctor.DoctorMainActivity;
import com.example.jaden.medicoapp.patientrecord.PatientRecord;

public class LoginActivity extends AppCompatActivity {

    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = mRadioGroup.getCheckedRadioButtonId();
                if (selectedId == R.id.login_patient) {
                    Intent registrationIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                    startActivity(registrationIntent);
                }else{
                    Toast.makeText(LoginActivity.this, "Doctor Registration via emails", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mRadioGroup = (RadioGroup) findViewById(R.id.login_group);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    public void next_button_function(View view) {
        int selectedId = mRadioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.login_patient) {
            Intent intent = new Intent(LoginActivity.this, PatientRecord.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(LoginActivity.this, DoctorMainActivity.class);
            startActivity(intent);
        }

    }

}