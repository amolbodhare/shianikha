package com.example.shianikha.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shianikha.R;
import com.example.shianikha.fragments.RegisterationScreenFirst;
import com.example.shianikha.fragments.RegisterationScreenSecond;

import java.util.ArrayList;
import java.util.List;

//public class RegisterationActivity extends AppCompatActivity implements RegisterationScreenFirst.OnFragmentInteractionListener, RegisterationScreenSecond.OnFragmentInteractionListener

public class RegisterationActivity extends AppCompatActivity
{
    private ArrayAdapter<String> arrayAdapter;
    //public  static FragmentManager fragmentManager;
    Button btn_next;
    TextView login_here_tv;
    ImageView gender_male_imv;
    ImageView gender_female_imv;

    EditText city_ed;


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
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Spinner spinner = (Spinner) findViewById(R.id.spinner);

        btn_next=findViewById(R.id.btn_next);

        login_here_tv=findViewById(R.id.login_here);
        gender_male_imv=findViewById(R.id.genderr_male_imv);
        gender_female_imv=findViewById(R.id.genderr_female_imv);

        city_ed=findViewById(R.id.city_ed);

        gender_male_imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gender_male_imv.setBackground(getResources().getDrawable(R.drawable.reg_img_bg_selected));
                gender_female_imv.setBackground(getResources().getDrawable(R.drawable.reg_img_bg));

            }
        });

        gender_female_imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gender_female_imv.setBackground(getResources().getDrawable(R.drawable.reg_img_bg_selected));
                gender_male_imv.setBackground(getResources().getDrawable(R.drawable.reg_img_bg));
            }
        });


        login_here_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(RegisterationActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                /*Fragment fragment=new RegisterationScreenSecond();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragent_frame,fragment,null)
                        .addToBackStack(null)
                        .commit();

*/
                Intent i=new Intent(RegisterationActivity.this,RegSecondPageActivity.class);
                //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                startActivity(i);



                /*new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {

                        startActivity(new Intent(RegisterationActivity.this,RegSecondPageActivity.class));
                        //finish();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                }, 1000);*/
            }
        });

        setUpEditTextClickListner();
        setUpTextWatcher();

        // Spinner click listener
        //spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        /*List<String> categories = new ArrayList<String>();
        categories.add("Profile For");
        categories.add("MySelf");
        categories.add("Daughter");
        categories.add("Son");
        categories.add("Sister");
        categories.add("Brother");*/

        // Creating adapter for spinner
        //ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);

        // Drop down layout style - list view with radio button
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // attaching data adapter to spinner
        //spinner.setAdapter(dataAdapter);


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


    private void setUpEditTextClickListner()
    {

        EditText editText = findViewById(R.id.city);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v);
            }
        });


        /*editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v);
            }
        });*/
    }

    private void setUpTextWatcher()
    {
        ((EditText)findViewById(R.id.editText)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setUpCustomSpinner(final View view)
    {
        ListView listView = findViewById(R.id.listView);
        findViewById(R.id.view).setVisibility(View.VISIBLE);
        findViewById(R.id.view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideCustomSpinnerLayout();
            }
        });

        if (view.getId()==R.id.city)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Search City");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,getResources().getStringArray(R.array.city));
            listView.setAdapter(arrayAdapter);
        }
       /* else if (view.getId() == R.id.state)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Search State");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,getResources().getStringArray(R.array.state));
            listView.setAdapter(arrayAdapter);
        }*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                TextView textView = v.findViewById(R.id.textView);
                if (textView!=null)
                {
                    Log.e("selectedIs",textView.getText().toString());
                    ((EditText)view).setText(textView.getText().toString());

                }
                hideCustomSpinnerLayout();
            }
        });

        findViewById(R.id.includeContainer).animate().translationX(0).setDuration(500);
    }

    private void hideCustomSpinnerLayout() {
        int i = findViewById(R.id.includeContainer).getWidth();
        findViewById(R.id.includeContainer).animate().translationX(i).setDuration(500);
        findViewById(R.id.view).setVisibility(View.GONE);
    }

}
