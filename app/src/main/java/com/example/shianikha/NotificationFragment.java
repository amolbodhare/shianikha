package com.example.shianikha;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.adoisstudio.helper.LoadingDialog;


public class NotificationFragment extends Fragment {

    View fragmentView;
    Context context;
    LoadingDialog loadingDialog;
    ListAdapter listAdapter;
    public static Fragment previousFragment;
    public static String previousFragmentName;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters

    public static NotificationFragment newInstance(Fragment fragment, String string)
    {
        NotificationFragment notificationFragment = new NotificationFragment();
        previousFragment = fragment;
        previousFragmentName = string;

        return notificationFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context =getContext();
        loadingDialog = new LoadingDialog(context);
        if(fragmentView==null)
        {
            fragmentView=inflater.inflate(R.layout.fragment_notification,null,false);
            listAdapter=new ListAdapter();
            ListView listView = fragmentView.findViewById(R.id.notificationList);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {

                    NotifacationDetails editProfileFragment = NotifacationDetails.newInstance("frgfg","fgf");



                }
            });
        }
        return fragmentView;
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
}
