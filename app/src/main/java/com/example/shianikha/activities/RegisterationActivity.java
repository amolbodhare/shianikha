package com.example.shianikha.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.Session;
import com.example.shianikha.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//public class RegisterationActivity extends AppCompatActivity implements RegisterationScreenFirst.OnFragmentInteractionListener, RegisterationScreenSecond.OnFragmentInteractionListener

public class RegisterationActivity extends AppCompatActivity
{
    private ArrayAdapter<String> arrayAdapter;
    //public  static FragmentManager fragmentManager;
    Button btn_next;
    TextView login_here_tv;
    ImageView gender_male_imv;
    ImageView gender_female_imv;
    EditText profile_for_ed;
    ArrayList<String> profile_for_arrayList;
    String masterdatajsonstring;
    Context context;
    MyBounceInterpolator interpolator;
    TextInputEditText full_name_tie;
    TextInputEditText email_tie;
    TextInputEditText mobile_number_tie;
    String gender="";


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
        context=RegisterationActivity.this;
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Spinner spinner = (Spinner) findViewById(R.id.spinner);

        btn_next=findViewById(R.id.btn_next);

        login_here_tv=findViewById(R.id.login_here);
        gender_male_imv=findViewById(R.id.genderr_male_imv);
        gender_female_imv=findViewById(R.id.genderr_female_imv);

        full_name_tie=findViewById(R.id.full_name);
        email_tie=findViewById(R.id.email);
        mobile_number_tie=findViewById(R.id.mobile_no);


        profile_for_ed=findViewById(R.id.profile_for_ed);
        profile_for_arrayList=new ArrayList<>();
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        interpolator = new MyBounceInterpolator(0.2, 20);

        try
        {

            //String masterdatajsonstring=getIntent().getExtras().getString("masterDataString");
            String masterdatajsonstring = new Session(context).getString(com.example.shianikha.commen.P.masterDataString);

            //System.out.print(masterdatajsonstring);
            Json json=new Json(masterdatajsonstring);

            JSONArray jsonArray=json.getJsonArray("profile_for");

            for(int i=0;i<jsonArray.length();i++)
            {
                profile_for_arrayList.add(jsonArray.getJSONObject(i).getString("name"));

            }

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        gender_male_imv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                gender_male_imv.setBackground(getResources().getDrawable(R.drawable.reg_img_bg_selected));
                gender_female_imv.setBackground(getResources().getDrawable(R.drawable.reg_img_bg));
                gender="male";
                myAnim.setInterpolator(interpolator);
                gender_male_imv.startAnimation(myAnim);
                gender_female_imv.clearAnimation();

            }
        });

        gender_female_imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gender_female_imv.setBackground(getResources().getDrawable(R.drawable.reg_img_bg_selected));
                gender_male_imv.setBackground(getResources().getDrawable(R.drawable.reg_img_bg));
                gender="female";
                myAnim.setInterpolator(interpolator);
                gender_female_imv.startAnimation(myAnim);
                gender_male_imv.clearAnimation();
            }
        });


        login_here_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(RegisterationActivity.this,LoginActivity.class);
                startActivity(i);
                //finish();
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




                if(validation())
                {

                    profile_for_ed.getTag();

                    Json json = new Json();
                    json.addString("profile_for", profile_for_ed.getTag().toString());
                    json.addString("name",full_name_tie.getText().toString());
                    json.addString("email", email_tie.getText().toString());
                    json.addString("ph_number",mobile_number_tie.getText().toString() );
                    json.addString("gender", gender);

                    new Session(context).addString("reg_data", json.toString());
                    Intent i=new Intent(RegisterationActivity.this,OTPVereificationActivty.class);
                    i.putExtra("masterDataString",masterdatajsonstring);

                    startActivity(i);
                }



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

        EditText editText = findViewById(R.id.profile_for_ed);

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

        if (view.getId()==R.id.profile_for_ed)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Profile for");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,profile_for_arrayList);
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
                    ((EditText)view).setTag(position);

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

    class MyBounceInterpolator implements android.view.animation.Interpolator {
        private double mAmplitude = 1;
        private double mFrequency = 10;

        MyBounceInterpolator(double amplitude, double frequency) {
            mAmplitude = amplitude;
            mFrequency = frequency;
        }

        public float getInterpolation(float time) {
            return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) *
                    Math.cos(mFrequency * time) + 1);
        }
    }

    public  boolean validation()
    {
        if(profile_for_ed.getText().toString().trim().equalsIgnoreCase(""))
        {
            profile_for_ed.setError("Select Profile for");

            return false;
        }

        else if(full_name_tie.getText().toString().trim().equalsIgnoreCase(""))
        {
            full_name_tie.setError("Enter full name");
            return false;
        }
        else if(email_tie.getText().toString().trim().equalsIgnoreCase(""))
        {
            email_tie.setError("Enter the email");
            return false;
        }
        else if(!isEmailValid(email_tie.getText().toString().trim()))
        {
            email_tie.setError("Enter the valid email");
            return false;
        }
        else if(mobile_number_tie.getText().toString().trim().equalsIgnoreCase(""))
        {
            mobile_number_tie.setError("Enter mobile number one");
            return false;
        }
        else if(gender.trim().equalsIgnoreCase(""))
        {
            Toast.makeText(context, "Select the  Gender please", Toast.LENGTH_SHORT).show();
            return false;
        }

        return  true;
    }


    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

}
