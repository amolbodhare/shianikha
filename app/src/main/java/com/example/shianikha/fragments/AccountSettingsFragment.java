package com.example.shianikha.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adoisstudio.helper.LoadingDialog;
import com.example.shianikha.NotifacationDetails;
import com.example.shianikha.R;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;


public class AccountSettingsFragment extends Fragment implements View.OnClickListener
{

    View fragmentView;
    Context context;
    RelativeLayout email_address_link_layout;
    RelativeLayout change_password_link_layout;
    ExpandableRelativeLayout email_address_exp_layout;
    ExpandableRelativeLayout change_password_exp_layout;
    TextView edit_password_tv_btn;
    EditText change_password_edt;
   /* LoadingDialog loadingDialog;
    ListAdapter listAdapter;*/

    private OnFragmentInteractionListener mListener;

    public AccountSettingsFragment() {

    }


    // TODO: Rename and change types and number of parameters

    public static AccountSettingsFragment newInstance()
    {
        AccountSettingsFragment fragment = new AccountSettingsFragment();

        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context =getContext();
        //loadingDialog = new LoadingDialog(context);
        if(fragmentView==null)
        {
            fragmentView=inflater.inflate(R.layout.fragment_account_settings,null,false);

            edit_password_tv_btn=fragmentView.findViewById(R.id.edit_password_tv_btn);
            change_password_edt=fragmentView.findViewById(R.id.change_password_edt);

            email_address_link_layout=fragmentView.findViewById(R.id.email_address_link_layout);
            change_password_link_layout=fragmentView.findViewById(R.id.change_password_link_layout);

            email_address_exp_layout=fragmentView.findViewById(R.id.email_address_exp_layout);
            change_password_exp_layout=fragmentView.findViewById(R.id.change_password_exp_layout);

            change_password_edt.setEnabled(false);
            email_address_exp_layout.collapse();
            change_password_exp_layout.collapse();


            edit_password_tv_btn.setOnClickListener(this);

            email_address_link_layout.setOnClickListener(this);
            change_password_link_layout.setOnClickListener(this);

            email_address_exp_layout.setOnClickListener(this);
            change_password_exp_layout.setOnClickListener(this);

            /*listAdapter=new ListAdapter();
            ListView listView = fragmentView.findViewById(R.id.notificationList);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {

                    NotifacationDetails editProfileFragment = NotifacationDetails.newInstance("frgfg","fgf");

                    try
                    {
                        fragmentLoader(editProfileFragment);
                    }

                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            });*/
        }
        return fragmentView;
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.email_address_link_layout)
        {

            email_address_exp_layout.toggle();
        }

        if(v.getId()==R.id.change_password_link_layout)
        {


            /*if(change_password_exp_layout.isExpanded())
            {

            }
            else {

            }*/

            change_password_edt.setEnabled(false);
            edit_password_tv_btn.setText("Edit");
            change_password_exp_layout.toggle();

        }

        if(v.getId()==R.id.edit_password_tv_btn)
        {

            if(((TextView)v).getText().equals("Edit")) {
                ((TextView)v).setText("Save");
                change_password_edt.setEnabled(true);
                change_password_edt.requestFocus();
                change_password_edt.setSelection(change_password_edt.getText().length());
            }

            else
                {
                    //((TextView)v).setText("edit");
                    //Toast.makeText(context, "hh", Toast.LENGTH_SHORT).show();
                    if(change_password_edt.getText().length()==0)
                    {
                        //we have to use setError here
                        Toast.makeText(context, "Empty password not allowed", Toast.LENGTH_SHORT).show();

                        InputMethodManager inputMethodManager =
                                (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInputFromWindow(
                                v.getApplicationWindowToken(),
                                InputMethodManager.SHOW_FORCED, 0);
                        change_password_edt.requestFocus();

                    }
                    else {

                        change_password_edt.setEnabled(false);
                        ((TextView)v).setText("Edit");

                    }


            }

        }


    }


    private class ListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null) {

                convertView = LayoutInflater.from(context).inflate(R.layout.notification_list_item,null,false);
            }
            return convertView;
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void fragmentLoader(Fragment fragment)
    {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit)
                .replace(R.id.frameLayout, fragment).commit();
    }


    private void fragmentLoaderSec(Fragment fragment)
    {
        FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();

        manager.beginTransaction()
                .setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit)
                .replace(R.id.frameLayout, fragment).commit();
    }

}
