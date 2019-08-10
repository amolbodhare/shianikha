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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.example.App;
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

public class SearchOptionFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    private View fragmentView;

    private Context context;
    private Session session;

    public SearchOptionFragment() {
        // Required empty public constructor
    }

    public static SearchOptionFragment newInstance() {
        SearchOptionFragment fragment = new SearchOptionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragmentView == null) {
            context = getActivity();
            session = new Session(context);
            fragmentView = inflater.inflate(R.layout.fragment_search_option, container, false);

            handleSeekBar();

        /*height_rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue)
            {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });*/

            setAllRequiredClickListener((ViewGroup)fragmentView.findViewById(R.id.linearLayout));
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
                /*H.log("minValueIs", minValue + "");
                H.log("maxValueIs", maxValue + "");*/

                long minProgress = (long) minValue;

                long maxProgress = (long) maxValue;

                long minFt = minProgress / 12;
                long minIn = minProgress % 12;

                long maxFt = maxProgress / 12;
                long maxIn = maxProgress % 12;

                //float minH = ((float) minFt / 3.281f) + ((float) minIn / 39.37f);//3.281  39.37
                String text = "" + minFt + "' " + minIn + '"';
                tvMinHeight.setText("Min " + text);
                tvMinHeight.setTag(minFt+"."+minIn);

                text = "" + maxFt + "' " + maxIn + '"';
                tvMaxHeight.setText("Max " + text);
                tvMaxHeight.setTag(maxFt+"."+maxIn);
            }
        });
    }

    @Override
    public void onClick(View view) {
        H.log("clickEvent", "isCalled");
        if (view.getId() == R.id.button)
            makeJson();
        else
            setUpMultiChoicePickerDialog(view);
    }

    private void makeJson() {
        Json json = new Json();
        json.addString(P.token_id,new Session(context).getString(P.tokenData));
        json.addString(P.search_now,"1");

        String string = ((TextView) fragmentView.findViewById(R.id.textMinAge)).getText().toString();
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

        string = ((EditText) fragmentView.findViewById(R.id.shiaCommunityEditText)).getText().toString();
        jsonArray = H.extractJsonArray(string,CommonListHolder.religionNameList,CommonListHolder.religionIdList);
        json.addJSONArray(P.religion_id, jsonArray);

        string = ((EditText) fragmentView.findViewById(R.id.motherTongueEditText)).getText().toString();
        jsonArray = H.extractJsonArray(string,CommonListHolder.languageNameList,CommonListHolder.languageIdList);
        json.addJSONArray(P.mother_tongue_id, jsonArray);

        string = ((EditText) fragmentView.findViewById(R.id.countryEditText)).getText().toString();
        jsonArray = H.extractJsonArray(string,CommonListHolder.countryNameList,CommonListHolder.countryIdList);
        json.addJSONArray(P.country, jsonArray);

        string = ((EditText) fragmentView.findViewById(R.id.stateEditText)).getText().toString();
        jsonArray = H.extractJsonArray(string,CommonListHolder.stateNameList,CommonListHolder.stateIdList);
        json.addJSONArray(P.state, jsonArray);

        string = ((EditText) fragmentView.findViewById(R.id.cityEditText)).getText().toString();
        jsonArray = H.extractJsonArray(string,CommonListHolder.cityNameList,CommonListHolder.cityIdList);
        json.addJSONArray(P.city, jsonArray);

        hitSearchOptionApi(json);
    }

    private void setUpMultiChoicePickerDialog(final View view) {
        if (view.getId() == R.id.maritalStatusEditText)
        {
            H.log("clickEvent", "isCalled");
            String[] array = new String[CommonListHolder.maritalStatusNameList.size()];
            array = CommonListHolder.maritalStatusNameList.toArray(array);
            showMaritalStatusMultiChoiceList(array, view);
        } else if (view.getId() == R.id.shiaCommunityEditText) {
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
        }
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

    private void hitSearchOptionApi(Json json)
    {
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
                .run("hitSearchApi");
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
