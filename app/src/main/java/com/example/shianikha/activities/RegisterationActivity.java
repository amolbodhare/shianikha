package com.example.shianikha.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shianikha.R;
import com.example.shianikha.fragments.RegisterationScreenFirst;
import com.example.shianikha.fragments.RegisterationScreenSecond;

import java.util.ArrayList;
import java.util.List;

//public class RegisterationActivity extends AppCompatActivity implements RegisterationScreenFirst.OnFragmentInteractionListener, RegisterationScreenSecond.OnFragmentInteractionListener

public class RegisterationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    //public  static FragmentManager fragmentManager;
    Button btn_next;

    /*@Override
    public void onFragmentInteraction(Uri uri) {

        Toast.makeText(getApplicationContext(),"Hi",Toast.LENGTH_SHORT).show();
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        btn_next=findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Fragment fragment=new RegisterationScreenSecond();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragent_frame,fragment,null)
                        .addToBackStack(null)
                        .commit();

*/

                Intent i=new Intent(RegisterationActivity.this,RegSecondPageActivity.class);
                //overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                startActivity(i);
            }
        });

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Profile For");
        categories.add("MySelf");
        categories.add("Daughter");
        categories.add("Son");
        categories.add("Sister");
        categories.add("Brother");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        /*fragmentManager=getSupportFragmentManager();

        if(findViewById(R.id.fragent_frame)!=null)
        {
            if(savedInstanceState!=null)
            {
                return;
            }

            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            Fragment fragment=new RegisterationScreenFirst();
            fragmentTransaction.add(R.id.fragent_frame,fragment,null);
            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
*/
        /*try
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragent_frame,fragment,fragment.getClass().getSimpleName()).addToBackStack(null).commit();
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }*/
        //Fragment fragment = new RegisterationScreenFirst();
      /*  FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragent_frame, fragment);
        fragmentTransaction.addToBackStack("RegisterationScreenFirst");
        fragmentTransaction.commit();*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();super.onBackPressed();
        /*FragmentManager fm = RegisterationActivity.this.getSupportFragmentManager();

        for(int i = 0; i < fm.getBackStackEntryCount()-1; i++)
        {
            fm.popBackStack();
        }
*/    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
