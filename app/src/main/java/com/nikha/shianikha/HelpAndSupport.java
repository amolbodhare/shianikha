package com.nikha.shianikha;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

import androidx.fragment.app.Fragment;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.nikha.shianikha.activities.HomeActivity;
import com.nikha.shianikha.commen.C;
import com.nikha.shianikha.commen.CommonListHolder;
import com.nikha.shianikha.commen.P;
import com.nikha.shianikha.commen.RequestModel;


public class HelpAndSupport extends Fragment implements View.OnClickListener {
    public static Fragment previousFragment;
    public static String previousFragmentName;
    private ArrayAdapter<String> arrayAdapter;
    View fragmentView;
    Context context;
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
        if (fragmentView == null) {
            context = getContext();
            fragmentView = inflater.inflate(R.layout.fragment_help_and_support, container, false);
            editText = fragmentView.findViewById(R.id.typeOfIssueEditText);
            fragmentView.findViewById(R.id.submitButton).setOnClickListener(this);
            setUpEditTextListener();
        }
        // Inflate the layout for this fragment
        return fragmentView;
    }

    private void setUpEditTextListener() {
        final EditText editText = fragmentView.findViewById(R.id.typeOfIssueEditText);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpCustomSpinner(editText);
            }
        });
    }

    private void setUpCustomSpinner(final EditText editText) {
        ListView listView = fragmentView.findViewById(R.id.listView);
        fragmentView.findViewById(R.id.view).setVisibility(View.VISIBLE);
        fragmentView.findViewById(R.id.view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideCustomSpinnerLayout();
            }
        });

        arrayAdapter = new ArrayAdapter<>(context, R.layout.text_view, R.id.textView, CommonListHolder.typeOfIssueNameList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                TextView textView = v.findViewById(R.id.textView);
                if (textView != null) {
                    Log.e("selectedIs", textView.getText().toString());
                    editText.setText(textView.getText().toString());

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
        if (v.getId() == R.id.submitButton) {
            makeJson();
        }
    }

    private void makeJson() {
        Json json = new Json();

        String string = editText.getText().toString().trim();
        if (string.isEmpty()) {
            H.showMessage(context, "Please select your profile for!");
            return;
        }
        int i = CommonListHolder.typeOfIssueNameList.indexOf(string);
        string = i == -1 ? "" : CommonListHolder.typeOfIssueIdList.get(i);
        json.addString(P.type_of_issue, string);

        string = ((EditText)fragmentView.findViewById(R.id.descriptionEditText)).getText().toString();
        json.addString(P.description,string);

        hitHelpAndSupportApi(json);
    }

    private void hitHelpAndSupportApi(Json json)
    {
        final LoadingDialog loadingDialog = new LoadingDialog(context);
        Session session = new Session(context);
        String string = session.getString(P.tokenData);
        json.addString(P.token_id, string);

        RequestModel requestModel = RequestModel.newRequestModel("type_of_issue");
        requestModel.addJSON(P.data, json);

        Api.newApi(context, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders())
                .setMethod(Api.POST)
                .onLoading(new Api.OnLoadingListener() {
                    @Override
                    public void onLoading(boolean isLoading) {
                        if (!((HomeActivity)context).isDestroyed()) {
                            if (isLoading)
                                loadingDialog.show();
                            else
                                loadingDialog.dismiss();
                        }
                    }
                })
                .onError(new Api.OnErrorListener() {
                    @Override
                    public void onError() {
                        H.showMessage(context, "Something went Wrong");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json) {

                        if (json.getInt(P.status) == 1)
                        {
                            H.showMessage(context,"Thanks for your help and support.");
                            ((HomeActivity)context).fragmentLoader(((HomeActivity)context).homeFragment,getString(R.string.dashboard));
                        } else
                            H.showMessage(context, json.getString(P.msg));
                    }
                })
                .run("hitHelpAndSupportApi");
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
