package com.example.shianikha.commen;

import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class CommonListHolder
{
    public static ArrayList<String> profileForNameList = new ArrayList<>(), profileForIdList = new ArrayList<>(),
            cityNameList = new ArrayList<>(), cityIdList = new ArrayList<>(), stateNameList = new ArrayList<>(),
            stateIdList =new ArrayList<>(), countryNameList = new ArrayList<>(), countryIdList = new ArrayList<>(),
            countryCodeList = new ArrayList<>(), heightList = new ArrayList<>(), religionNameList = new ArrayList<>(),
            religionIdList = new ArrayList<>(), ethnicityNameList = new ArrayList<>(), ethnicityIdList = new ArrayList<>(),
            occupationNameList = new ArrayList<>(), occupationIdList = new ArrayList<>(), educationNameList = new ArrayList<>(),
            educationIdList = new ArrayList<>(), languageNameList = new ArrayList<>(), languageIdList = new ArrayList<>(),
            smokingNameList = new ArrayList<>(), smokingIdList = new ArrayList<>(), relocateNameList = new ArrayList<>(),
            relocateIdList = new ArrayList<>(), intrestedInNameList = new ArrayList<>(),intrestedInIdList = new ArrayList<>(),
            complexionNameList = new ArrayList<>(), complexionIdList = new ArrayList<>(), ageList = new ArrayList<>(),
            maritalStatusNameList = new ArrayList<>(), maritalStatusIdList = new ArrayList<>(), seekingForMarriageNameList = new ArrayList<>(),
            seekingForMarriageIdList = new ArrayList<>(), physicalStatusNameList = new ArrayList<>(), physicalStatusIdList = new ArrayList<>(),
            monthlyIncomeNameList = new ArrayList<>(), monthlyIncomeIdList = new ArrayList<>(), typeOfIssueNameList = new ArrayList<>(),
            typeOfIssueIdList = new ArrayList<>();

    public void makeProfileForList(JsonList jsonList) {
        profileForNameList = new ArrayList<>();
        profileForIdList = new ArrayList<>();

        for (Json json : jsonList) {
            profileForNameList.add(json.getString(P.name));

            profileForIdList.add(json.getString(P.id));
        }
    }

    public void makeCityList(JsonList jsonList) {
        cityNameList = new ArrayList<>();
        cityIdList = new ArrayList<>();

        for (Json json : jsonList) {
            cityNameList.add(json.getString(P.city_name));

            cityIdList.add(json.getString(P.city_id));
        }
    }

    public void makeStateList(JsonList jsonList) {
        stateNameList = new ArrayList<>();
        stateIdList = new ArrayList<>();

        for (Json json : jsonList) {
            stateNameList.add(json.getString(P.state_name));

            stateIdList.add(json.getString(P.state_id));
        }
    }

    public void makeCountryList(JsonList jsonList) {
        countryNameList = new ArrayList<>();
        countryIdList = new ArrayList<>();
        countryCodeList = new ArrayList<>();

        for (Json json : jsonList) {
            countryNameList.add(json.getString(P.name));

            countryIdList.add(json.getString(P.id));

            countryCodeList.add(json.getString(P.country_code));
        }
    }

    public void makeHeightList(JSONArray jsonArray) {
        heightList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                heightList.add(jsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void makeReligionList(JsonList jsonList) {
        religionNameList = new ArrayList<>();
        religionIdList = new ArrayList<>();

        for (Json json : jsonList) {
            religionNameList.add(json.getString(P.name));

            religionIdList.add(json.getString(P.id));
        }
    }

    public void makeEthnicityList(JsonList jsonList) {
        ethnicityNameList = new ArrayList<>();
        ethnicityIdList = new ArrayList<>();

        for (Json json : jsonList) {
            ethnicityNameList.add(json.getString(P.name));

            ethnicityIdList.add(json.getString(P.id));
        }
    }

    public void makeOccupationList(JsonList jsonList) {
        occupationNameList = new ArrayList<>();
        occupationIdList = new ArrayList<>();

        for (Json json : jsonList) {
            occupationNameList.add(json.getString(P.name));

            occupationIdList.add(json.getString(P.id));
        }
    }

    public void makeEducationList(JsonList jsonList) {
        educationNameList = new ArrayList<>();
        educationIdList = new ArrayList<>();

        for (Json json : jsonList) {
            educationNameList.add(json.getString(P.name));

            educationIdList.add(json.getString(P.id));
        }
    }

    public void makeMotherTongueList(JsonList jsonList) {
        languageNameList = new ArrayList<>();
        languageIdList = new ArrayList<>();

        for (Json json : jsonList) {
            languageNameList.add(json.getString(P.name));

            languageIdList.add(json.getString(P.id));
        }
    }

    public void makeSmokingList(JsonList jsonList) {
        smokingNameList = new ArrayList<>();
        smokingIdList= new ArrayList<>();

        for (Json json : jsonList) {
            smokingNameList.add(json.getString(P.val));

            smokingIdList.add(json.getString(P.id));
        }
    }

    public void makeRelocateList(JsonList jsonList) {
        relocateNameList = new ArrayList<>();
        relocateIdList= new ArrayList<>();

        for (Json json : jsonList) {
            relocateNameList.add(json.getString(P.val));

            relocateIdList.add(json.getString(P.id));
        }
    }

    public void makeIntrestedInList(JsonList jsonList) {
        intrestedInNameList = new ArrayList<>();
        intrestedInIdList= new ArrayList<>();

        for (Json json : jsonList) {
            intrestedInNameList.add(json.getString(P.name));

            intrestedInIdList.add(json.getString(P.id));
        }
    }

    public void makeComplexionList(JsonList jsonList) {
        complexionNameList = new ArrayList<>();
        complexionIdList= new ArrayList<>();

        for (Json json : jsonList) {
            complexionNameList.add(json.getString(P.val));

            complexionIdList.add(json.getString(P.id));
        }
    }

    public void makeAgeList(JSONArray jsonArray) {
        ageList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                ageList.add(jsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void makeMaritalStatusList(JsonList jsonList) {
        maritalStatusNameList = new ArrayList<>();
        maritalStatusIdList= new ArrayList<>();

        for (Json json : jsonList) {
            maritalStatusNameList.add(json.getString(P.val));

            maritalStatusIdList.add(json.getString(P.id));
        }
    }

    public void makeSeekingForMarriageList(JsonList jsonList) {
        seekingForMarriageNameList = new ArrayList<>();
        seekingForMarriageIdList= new ArrayList<>();

        for (Json json : jsonList) {
            seekingForMarriageNameList.add(json.getString(P.val));

            seekingForMarriageIdList.add(json.getString(P.id));
        }
    }

    public void makePhysicalStatusList(JsonList jsonList) {
        physicalStatusNameList = new ArrayList<>();
        physicalStatusIdList= new ArrayList<>();

        for (Json json : jsonList) {
            physicalStatusNameList.add(json.getString(P.val));

            physicalStatusIdList.add(json.getString(P.id));
        }
    }

    public void makeMonthlyIncomeList(JsonList jsonList)
    {
        monthlyIncomeNameList = new ArrayList<>();
        monthlyIncomeIdList= new ArrayList<>();

        for (Json json : jsonList) {
            monthlyIncomeNameList.add(json.getString(P.name));

            monthlyIncomeIdList.add(json.getString(P.id));
        }
    }

    public void makeIssueList(JsonList jsonList) {
        typeOfIssueNameList = new ArrayList<>();
        typeOfIssueIdList= new ArrayList<>();

        for (Json json : jsonList) {
            typeOfIssueNameList.add(json.getString(P.name));

            typeOfIssueIdList.add(json.getString(P.id));
        }
    }
}
