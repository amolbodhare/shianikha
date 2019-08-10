package com.example.shianikha;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.LoadingDialog;
import com.example.shianikha.commen.CommonListHolder;
import com.example.shianikha.commen.P;


public class HelpAndSupport extends Fragment implements View.OnClickListener {
    public static Fragment previousFragment;
    public static String previousFragmentName;
    private ArrayAdapter<String> arrayAdapter;
    View fragmentView;
    Context context;
    LoadingDialog loadingDialog;
    EditText editText;

    private OnFragmentInteractionListener mListener;

    public HelpAndSupport() {
        // Required empty public constructor
    }

    public static HelpAndSupport newInstance() {
        HelpAndSupport fragment = new HelpAndSupport();

        return fragment;
    }

    // TODO: Rename and change types and number of parameters

    public static HelpAndSupport newInstance(Fragment fragment, String string) {
        HelpAndSupport helpAndSupportFragment = new HelpAndSupport();
        previousFragment = fragment;
        previousFragmentName = string;

        return helpAndSupportFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(fragmentView == null)
        {
            context = getContext();
            loadingDialog = new LoadingDialog(context);
            fragmentView = inflater.inflate(R.layout.fragment_help_and_support, container, false);
            editText = fragmentView.findViewById(R.id.typeOfIssueEditText);
            fragmentView.findViewById(R.id.btn_next).setOnClickListener(this);
            setUpEditTextListener();
        }
        // Inflate the layout for this fragment
        return fragmentView;
    }

    private void setUpEditTextListener() {
        EditText editText = fragmentView.findViewById(R.id.typeOfIssueEditText);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpCustomSpinner(v);
            }
        });
    }

    private void setUpCustomSpinner(final View view) {
        ListView listView = fragmentView.findViewById(R.id.listView);
        fragmentView.findViewById(R.id.view).setVisibility(View.VISIBLE);
        fragmentView.findViewById(R.id.view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideCustomSpinnerLayout();
            }
        });
        if (view.getId() == R.id.typeOfIssueEditText) {
            arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, CommonListHolder.typeOfIssueNameList);
            listView.setAdapter(arrayAdapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                TextView textView = view.findViewById(R.id.textView);
                if (textView != null) {
                    Log.e("selectedIs", textView.getText().toString());
                    ((EditText) view).setText(textView.getText().toString());

                }
                hideCustomSpinnerLayout();
            }
        });

        fragmentView.findViewById(R.id.includeContainer).animate().translationX(0).setDuration(500);
    }

    private void hideCustomSpinnerLayout() {

        int i = fragmentView.findViewById(R.id.includeContainer).getWidth();
        fragmentView.findViewById(R.id.includeContainer).animate().translationX(i).setDuration(500);
        fragmentView.findViewById(R.id.view).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_next) {
            makeJson();
        }
    }

    private void makeJson() {
        Json json = new Json();

        String string = editText.getText().toString().trim();

        if (string.isEmpty())
        {
            H.showMessage(context,"Please select your profile for!");
            return;
        }
        int i= CommonListHolder.typeOfIssueNameList.indexOf(string);
        if (i!=-1)
        {
            string = CommonListHolder.typeOfIssueIdList.get(i);
            json.addString(P.type_of_issue,string);
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Object object = fragmentView.getParent();
        if (object instanceof FrameLayout)
            ((FrameLayout) object).removeAllViews();
    }
}
