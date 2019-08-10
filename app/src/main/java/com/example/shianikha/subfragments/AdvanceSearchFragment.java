package com.example.shianikha.subfragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.example.shianikha.R;
import com.example.shianikha.activities.HomeActivity;
import com.example.shianikha.commen.C;
import com.example.shianikha.commen.CommonListHolder;
import com.example.shianikha.commen.P;
import com.example.shianikha.commen.RequestModel;
import com.example.shianikha.fragments.FavouritesFragment;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class AdvanceSearchFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    LinearLayout parent_layout;
    View fragmentView;
    Context context;

    public AdvanceSearchFragment() {
        // Required empty public constructor
    }

    public static AdvanceSearchFragment newInstance() {
        AdvanceSearchFragment fragment = new AdvanceSearchFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_advance_search, container, false);
            context = getActivity();

            handleSeekBar();
            makeEthnicityCheckBox();
            makeEducationCheckBox();

            setAllRequiredClickListener((LinearLayout) fragmentView.findViewById(R.id.linearLayout));
            fragmentView.findViewById(R.id.button).setOnClickListener(this);
        }

        return fragmentView;
    }

    private void setAllRequiredClickListener(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup)
                setAllRequiredClickListener((ViewGroup) view);
            else if (view instanceof EditText && !view.isFocusable())
                view.setOnClickListener(this);
        }
    }

    private void makeEducationCheckBox() {
        LinearLayout linearLayout = fragmentView.findViewById(R.id.educationContainer);
        for (int i = 0; i < CommonListHolder.educationNameList.size(); i++) {
            LinearLayout ll = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.inflatable_check_box, null);
            CheckBox checkBox = (CheckBox) ll.getChildAt(0);
            checkBox.setText(CommonListHolder.educationNameList.get(i));
            checkBox.setTag(CommonListHolder.educationIdList.get(i));

            linearLayout.addView(ll);
        }
    }

    private void makeEthnicityCheckBox() {
        int n = CommonListHolder.ethnicityNameList.size();
        int i = n % 2 + n / 2;

        LinearLayout linearLayout = fragmentView.findViewById(R.id.ethnicityContainer1);
        for (int j = 0; j < n; j++) {
            LinearLayout ll = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.inflatable_check_box, null);
            CheckBox checkBox = (CheckBox) ll.getChildAt(0);
            checkBox.setText(CommonListHolder.ethnicityNameList.get(j));
            checkBox.setTag(CommonListHolder.ethnicityIdList.get(j));

            linearLayout.addView(ll);

            if (j == i - 1)
                linearLayout = fragmentView.findViewById(R.id.ethnicityContainer2);
        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();

        if (i == R.id.button)
            makeJson();
        else
            setUpMultiChoicePickerDialog(view);
    }

    private void setUpMultiChoicePickerDialog(final View view) {
        if (view.getId() == R.id.maritalStatusEditText) {
            String[] array = new String[CommonListHolder.maritalStatusNameList.size()];
            array = CommonListHolder.maritalStatusNameList.toArray(array);
            showMaritalStatusMultiChoiceList(array, view);
        } else if (view.getId() == R.id.religionEditText) {
            String[] array = new String[CommonListHolder.religionNameList.size()];
            array = CommonListHolder.religionNameList.toArray(array);
            showReligionMultiChoiceList(array, view);
        } else if (view.getId() == R.id.motherTongueEditText) {
            String[] array = new String[CommonListHolder.languageNameList.size()];
            array = CommonListHolder.languageNameList.toArray(array);
            showMotherTongueMultiChoiceList(array, view);
        } else if (view.getId() == R.id.countryEditText) {
            String[] array = new String[CommonListHolder.countryNameList.size()];
            array = CommonListHolder.countryNameList.toArray(array);
            showCountryMultiChoiceList(array, view);
        } else if (view.getId() == R.id.stateEditText) {
            String[] array = new String[CommonListHolder.stateNameList.size()];
            array = CommonListHolder.stateNameList.toArray(array);
            showStateMultiChoiceList(array, view);
        } else if (view.getId() == R.id.cityEditText) {
            String[] array = new String[CommonListHolder.cityNameList.size()];
            array = CommonListHolder.cityNameList.toArray(array);
            showCityMultiChoiceList(array, view);
        } else if (view.getId() == R.id.languageEditText) {
            String[] array = new String[CommonListHolder.languageNameList.size()];
            array = CommonListHolder.languageNameList.toArray(array);
            showLanguageMultiChoiceList(array, view);
        }
        else if (view.getId() == R.id.occupationEditText) {
            String[] array = new String[CommonListHolder.occupationNameList.size()];
            array = CommonListHolder.occupationNameList.toArray(array);
            showOccupationMultiChoiceList(array, view);
        }
    }

    private void makeJson()
    {
        Json json = new Json();
        json.addString(P.token_id,new Session(context).getString(P.tokenData));
        json.addString(P.full_search,"1");

        RadioGroup radioGroup = fragmentView.findViewById(R.id.lookingForRadioGroup);
        int i = radioGroup.getCheckedRadioButtonId();
        String string = radioGroup.findViewById(i).getTag().toString();
        json.addString(P.gender,string);

        string = ((TextView) fragmentView.findViewById(R.id.textMinAge)).getText().toString();
        string = H.extractNumberFromString(string);
        json.addString(P.min_age, string);

        string = ((TextView) fragmentView.findViewById(R.id.textMaxAge)).getText().toString();
        string = H.extractNumberFromString(string);
        json.addString(P.max_age, string);

        Object object =  fragmentView.findViewById(R.id.textMinHeight).getTag();
        if (object != null)
            string = object.toString();
        string = H.extractNumberFromString(string);
        json.addString(P.min_height, string);

        object =  fragmentView.findViewById(R.id.textMaxHeight).getTag();
        if (object != null)
            string = object.toString();
        string = H.extractNumberFromString(string);
        json.addString(P.max_height, string);

        string = ((EditText) fragmentView.findViewById(R.id.maritalStatusEditText)).getText().toString();
        JSONArray jsonArray = H.extractJsonArray(string,CommonListHolder.maritalStatusNameList,CommonListHolder.maritalStatusIdList);
        json.addJSONArray(P.marital_status, jsonArray);

        string = ((EditText) fragmentView.findViewById(R.id.religionEditText)).getText().toString();
        jsonArray = H.extractJsonArray(string,CommonListHolder.religionNameList,CommonListHolder.religionIdList);
        json.addJSONArray(P.religion_id, jsonArray);

        string = ((EditText) fragmentView.findViewById(R.id.motherTongueEditText)).getText().toString();
        jsonArray = H.extractJsonArray(string,CommonListHolder.languageNameList,CommonListHolder.languageIdList);
        json.addJSONArray(P.mother_tongue_id, jsonArray);

        string = ((EditText) fragmentView.findViewById(R.id.languageEditText)).getText().toString();
        jsonArray = H.extractJsonArray(string,CommonListHolder.languageNameList,CommonListHolder.languageIdList);
        json.addJSONArray(P.language_id, jsonArray);

        string = ((EditText) fragmentView.findViewById(R.id.countryEditText)).getText().toString();
        jsonArray = H.extractJsonArray(string,CommonListHolder.countryNameList,CommonListHolder.countryIdList);
        json.addJSONArray(P.country, jsonArray);

        string = ((EditText) fragmentView.findViewById(R.id.stateEditText)).getText().toString();
        jsonArray = H.extractJsonArray(string,CommonListHolder.stateNameList,CommonListHolder.stateIdList);
        json.addJSONArray(P.state, jsonArray);

        string = ((EditText) fragmentView.findViewById(R.id.cityEditText)).getText().toString();
        jsonArray = H.extractJsonArray(string,CommonListHolder.cityNameList,CommonListHolder.cityIdList);
        json.addJSONArray(P.city, jsonArray);

        string = ((EditText) fragmentView.findViewById(R.id.occupationEditText)).getText().toString();
        jsonArray = H.extractJsonArray(string,CommonListHolder.occupationNameList,CommonListHolder.occupationIdList);
        json.addJSONArray(P.occupation_id, jsonArray);

        jsonArray = makeEthnicityJsonArray();
        json.addJSONArray(P.ethnicity_id,jsonArray);

        jsonArray = makeEducationJsonArray();
        json.addJSONArray(P.edulevel_id,jsonArray);

        radioGroup = fragmentView.findViewById(R.id.syedRadioGroup);
        i = radioGroup.getCheckedRadioButtonId();
        string = radioGroup.findViewById(i).getTag().toString();
        json.addString(P.syed,string);

        radioGroup = fragmentView.findViewById(R.id.convertedRadioGroup);
        i = radioGroup.getCheckedRadioButtonId();
        string = radioGroup.findViewById(i).getTag().toString();
        json.addString(P.cvt_islam,string);

        radioGroup = fragmentView.findViewById(R.id.childrenRadioGroup);
        i = radioGroup.getCheckedRadioButtonId();
        string = radioGroup.findViewById(i).getTag().toString();
        json.addString(P.children,string);

        radioGroup = fragmentView.findViewById(R.id.disabilityRadioGroup);
        i = radioGroup.getCheckedRadioButtonId();
        string = radioGroup.findViewById(i).getTag().toString();
        json.addString(P.handicap,string);

        radioGroup = fragmentView.findViewById(R.id.namazRadioGroup);
        i = radioGroup.getCheckedRadioButtonId();
        string = radioGroup.findViewById(i).getTag().toString();
        json.addString(P.namaz,string);

        radioGroup = fragmentView.findViewById(R.id.rozaRadioGroup);
        i = radioGroup.getCheckedRadioButtonId();
        string = radioGroup.findViewById(i).getTag().toString();
        json.addString(P.roza,string);

        radioGroup = fragmentView.findViewById(R.id.hizabPreferenceRadioGroup);
        i = radioGroup.getCheckedRadioButtonId();
        string = radioGroup.findViewById(i).getTag().toString();
        json.addString(P.hijab_preference,string);

        CheckBox checkBox = fragmentView.findViewById(R.id.checkBox);
        string = checkBox.isChecked() ? "1" : "0";
        json.addString(P.show_profile_with_photo,string);

        hitSearch1Api(json);
    }

    private void hitSearch1Api(Json json) {
        final LoadingDialog loadingDialog = new LoadingDialog(context);

        RequestModel requestModel = RequestModel.newRequestModel("search");
        requestModel.addJSON(P.data, json);

        Api.newApi(context, P.baseUrl).addJson(requestModel).onHeaderRequest(C.getHeaders()).setMethod(Api.POST)
                .onLoading(new Api.OnLoadingListener()
                {
                    @Override
                    public void onLoading(boolean isLoading) {
                        if (isLoading)
                            loadingDialog.show("Please wait...");
                        else
                            loadingDialog.dismiss();
                    }
                })
                .onError(new Api.OnErrorListener() {
                    @Override
                    public void onError() {
                        H.showMessage(context, "Something went wrong.");
                    }
                })
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json)
                    {
                        if (json.getInt(P.status) == 1)
                        {
                            ((HomeActivity) context).favouritesFragment = FavouritesFragment.newInstance(HomeActivity.currentFragment, HomeActivity.currentFragmentName);

                            Bundle bundle = new Bundle();
                            bundle.putString(P.json,json.toString());
                            ((HomeActivity)context).favouritesFragment.setArguments(bundle);

                            ((HomeActivity) context).fragmentLoader(((HomeActivity) context).favouritesFragment, "Search result");
                        }
                    }
                })
                .run("hitSearch1Api");
    }

    private JSONArray makeEducationJsonArray()
    {
        JSONArray jsonArray = new JSONArray();

        LinearLayout linearLayout = fragmentView.findViewById(R.id.educationContainer);
        LinearLayout ll;
        CheckBox checkBox;
        String string = "";
        Object object;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            ll = (LinearLayout) linearLayout.getChildAt(i);
            checkBox = (CheckBox) ll.getChildAt(0);
            if (checkBox.isChecked()) {
                object = checkBox.getTag();
                if (object != null)
                    string = object.toString();

                jsonArray.put(string);
            }
        }

        return jsonArray;
    }

    private JSONArray makeEthnicityJsonArray()
    {
        JSONArray jsonArray = new JSONArray();

        LinearLayout linearLayout = fragmentView.findViewById(R.id.ethnicityContainer1);
        LinearLayout ll;
        CheckBox checkBox;
        String string = "";
        Object object;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            ll = (LinearLayout) linearLayout.getChildAt(i);
            checkBox = (CheckBox) ll.getChildAt(0);
            if (checkBox.isChecked()) {
                object = checkBox.getTag();
                if (object != null)
                    string = object.toString();

                jsonArray.put(string);
            }
        }

        linearLayout = fragmentView.findViewById(R.id.ethnicityContainer2);
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            ll = (LinearLayout) linearLayout.getChildAt(i);
            checkBox = (CheckBox) ll.getChildAt(0);
            if (checkBox.isChecked()) {
                object = checkBox.getTag();
                if (object != null)
                    string = object.toString();

                jsonArray.put(string);
            }
        }

        return jsonArray;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void handleSeekBar() {
        final CrystalRangeSeekbar age_rangeSeekbar = fragmentView.findViewById(R.id.age_rangeSeekbar);
        final CrystalRangeSeekbar height_rangeSeekbar = fragmentView.findViewById(R.id.height_rangeSeekbar);

        // get min and max text view
        final TextView tvMinAge = fragmentView.findViewById(R.id.textMinAge);
        final TextView tvMaxAge = fragmentView.findViewById(R.id.textMaxAge);

        final TextView tvMinHeight = fragmentView.findViewById(R.id.textMinHeight);
        final TextView tvMaxHeight = fragmentView.findViewById(R.id.textMaxHeight);

        age_rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {

                tvMinAge.setText("Min " + minValue + " years");
                tvMaxAge.setText("Max " + maxValue + " years");

            }
        });

        height_rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                //H.log("minValueIs", minValue + "");
                //H.log("maxValueIs", maxValue + "");

                long minProgress = (long) minValue;

                long maxProgress = (long) maxValue;

                long minFt = minProgress / 12;
                long minIn = minProgress % 12;

                long maxFt = maxProgress / 12;
                long maxIn = maxProgress % 12;


                /*int ft = progress/12;
                int in = progress%12;*/
                float minH = ((float) minFt / 3.281f) + ((float) minIn / 39.37f);//3.281  39.37
                String text = "" + minFt + "' " + minIn + '"';
                tvMinHeight.setText("Min " + text);
                tvMinHeight.setTag(minFt+"."+minIn);

                text = "" + maxFt + "' " + maxIn + '"';
                tvMaxHeight.setText("Max " + text);
                tvMaxHeight.setTag(maxFt+"."+maxIn);
            }
        });
    }

    private ArrayList<String> tempCityList;
    private boolean[] cityCheckedArray;
    private void showCityMultiChoiceList(final String[] array, final View view) {
        if (cityCheckedArray == null) {
            cityCheckedArray = new boolean[array.length];
            for (int j = 0; j < array.length; j++)
                cityCheckedArray[j] = false;
            tempCityList = new ArrayList<>();
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMultiChoiceItems(array, cityCheckedArray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if (b) {
                    tempCityList.add(array[i]);
                    cityCheckedArray[i] = true;
                } else {
                    tempCityList.remove(array[i]);
                    cityCheckedArray[i] = false;
                }
            }
        }).setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (tempCityList != null) {
                    String string = tempCityList.toString();
                    string = string.replace("[", "");
                    string = string.replace("]", "");

                    ((EditText) view).setText(string);
                }
            }
        }).show();
    }

    private ArrayList<String> tempStateList;
    private boolean[] stateCheckedArray;
    private void showStateMultiChoiceList(final String[] array, final View view) {
        if (stateCheckedArray == null) {
            stateCheckedArray = new boolean[array.length];
            for (int j = 0; j < array.length; j++)
                stateCheckedArray[j] = false;
            tempStateList = new ArrayList<>();
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMultiChoiceItems(array, stateCheckedArray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if (b) {
                    tempStateList.add(array[i]);
                    stateCheckedArray[i] = true;
                } else {
                    tempStateList.remove(array[i]);
                    stateCheckedArray[i] = false;
                }
            }
        }).setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (tempStateList != null) {
                    String string = tempStateList.toString();
                    string = string.replace("[", "");
                    string = string.replace("]", "");

                    ((EditText) view).setText(string);
                }
            }
        }).show();
    }

    private ArrayList<String> tempCountryList;
    private boolean[] countryCheckedArray;
    private void showCountryMultiChoiceList(final String[] array, final View view) {
        if (countryCheckedArray == null) {
            countryCheckedArray = new boolean[array.length];
            for (int j = 0; j < array.length; j++)
                countryCheckedArray[j] = false;
            tempCountryList = new ArrayList<>();
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMultiChoiceItems(array, countryCheckedArray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if (b) {
                    tempCountryList.add(array[i]);
                    countryCheckedArray[i] = true;
                } else {
                    tempCountryList.remove(array[i]);
                    countryCheckedArray[i] = false;
                }
            }
        }).setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (tempCountryList != null) {
                    String string = tempCountryList.toString();
                    string = string.replace("[", "");
                    string = string.replace("]", "");

                    ((EditText) view).setText(string);
                }
            }
        }).show();
    }

    private ArrayList<String> tempMotherTongueList;
    private boolean[] motherTongueCheckedArray;
    private void showMotherTongueMultiChoiceList(final String[] array, final View view) {
        if (motherTongueCheckedArray == null) {
            motherTongueCheckedArray = new boolean[array.length];
            for (int j = 0; j < array.length; j++)
                motherTongueCheckedArray[j] = false;
            tempMotherTongueList = new ArrayList<>();
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMultiChoiceItems(array, motherTongueCheckedArray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if (b) {
                    tempMotherTongueList.add(array[i]);
                    motherTongueCheckedArray[i] = true;
                } else {
                    tempMotherTongueList.remove(array[i]);
                    motherTongueCheckedArray[i] = false;
                }
            }
        }).setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (tempMotherTongueList != null) {
                    String string = tempMotherTongueList.toString();
                    string = string.replace("[", "");
                    string = string.replace("]", "");

                    ((EditText) view).setText(string);
                }
            }
        }).show();
    }

    private ArrayList<String> tempReligionList;
    private boolean[] religionCheckedArray;
    private void showReligionMultiChoiceList(final String[] array, final View view) {
        if (religionCheckedArray == null) {
            religionCheckedArray = new boolean[array.length];
            for (int j = 0; j < array.length; j++)
                religionCheckedArray[j] = false;
            tempReligionList = new ArrayList<>();
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMultiChoiceItems(array, religionCheckedArray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if (b) {
                    tempReligionList.add(array[i]);
                    religionCheckedArray[i] = true;
                } else {
                    tempReligionList.remove(array[i]);
                    religionCheckedArray[i] = false;
                }
            }
        }).setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (tempReligionList != null) {
                    String string = tempReligionList.toString();
                    string = string.replace("[", "");
                    string = string.replace("]", "");

                    ((EditText) view).setText(string);
                }
            }
        }).show();
    }

    private ArrayList<String> tempMaritalStatusList;
    private boolean[] maritalStatusCheckedArray;
    private void showMaritalStatusMultiChoiceList(final String[] array, final View view) {

        if (maritalStatusCheckedArray == null) {
            maritalStatusCheckedArray = new boolean[array.length];
            for (int j = 0; j < array.length; j++)
                maritalStatusCheckedArray[j] = false;
            tempMaritalStatusList = new ArrayList<>();
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMultiChoiceItems(array, maritalStatusCheckedArray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if (b) {
                    tempMaritalStatusList.add(array[i]);
                    maritalStatusCheckedArray[i] = true;
                } else {
                    tempMaritalStatusList.remove(array[i]);
                    maritalStatusCheckedArray[i] = false;
                }
            }
        }).setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (tempMaritalStatusList != null) {
                    String string = tempMaritalStatusList.toString();
                    string = string.replace("[", "");
                    string = string.replace("]", "");

                    ((EditText) view).setText(string);
                }
            }
        }).show();
    }

    private ArrayList<String> tempLanguageList;
    private boolean[] languageCheckedArray;
    private void showLanguageMultiChoiceList(final String[] array, final View view) {

        if (languageCheckedArray == null) {
            languageCheckedArray = new boolean[array.length];
            for (int j = 0; j < array.length; j++)
                languageCheckedArray[j] = false;
            tempLanguageList = new ArrayList<>();
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMultiChoiceItems(array, languageCheckedArray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if (b) {
                    tempLanguageList.add(array[i]);
                    languageCheckedArray[i] = true;
                } else {
                    tempLanguageList.remove(array[i]);
                    languageCheckedArray[i] = false;
                }
            }
        }).setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (tempLanguageList != null) {
                    String string = tempLanguageList.toString();
                    string = string.replace("[", "");
                    string = string.replace("]", "");

                    ((EditText) view).setText(string);
                }
            }
        }).show();
    }

    private ArrayList<String> tempOccupationList;
    private boolean[] occupationCheckedArray;
    private void showOccupationMultiChoiceList(final String[] array, final View view) {

        if (occupationCheckedArray == null) {
            occupationCheckedArray = new boolean[array.length];
            for (int j = 0; j < array.length; j++)
                occupationCheckedArray[j] = false;
            tempOccupationList = new ArrayList<>();
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMultiChoiceItems(array, occupationCheckedArray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if (b) {
                    tempOccupationList.add(array[i]);
                    occupationCheckedArray[i] = true;
                } else {
                    tempOccupationList.remove(array[i]);
                    occupationCheckedArray[i] = false;
                }
            }
        }).setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (tempOccupationList != null) {
                    String string = tempOccupationList.toString();
                    string = string.replace("[", "");
                    string = string.replace("]", "");

                    ((EditText) view).setText(string);
                }
            }
        }).show();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Object object = fragmentView.getParent();
        if (object instanceof FrameLayout)
            ((FrameLayout) object).removeAllViews();
    }
}
