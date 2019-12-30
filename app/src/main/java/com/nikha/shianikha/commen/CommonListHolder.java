package com.nikha.shianikha.commen;

import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.JsonList;

import org.json.JSONArray;

import java.util.ArrayList;

public class CommonListHolder {
    public static ArrayList<String> profileForNameList = new ArrayList<>(), profileForIdList = new ArrayList<>(),
            cityNameList = new ArrayList<>(), cityIdList = new ArrayList<>(), stateNameList = new ArrayList<>(),
            stateIdList = new ArrayList<>(), countryNameList = new ArrayList<>(), countryIdList = new ArrayList<>(),
            countryCodeList = new ArrayList<>(), heightList = new ArrayList<>(), religionNameList = new ArrayList<>(),
            religionIdList = new ArrayList<>(), ethnicityNameList = new ArrayList<>(), ethnicityIdList = new ArrayList<>(),
            occupationNameList = new ArrayList<>(), occupationIdList = new ArrayList<>(), educationNameList = new ArrayList<>(),
            educationIdList = new ArrayList<>(), languageNameList = new ArrayList<>(), languageIdList = new ArrayList<>(),
            smokingNameList = new ArrayList<>(), smokingIdList = new ArrayList<>(), relocateNameList = new ArrayList<>(),
            relocateIdList = new ArrayList<>(), intrestedInNameList = new ArrayList<>(), intrestedInIdList = new ArrayList<>(),
            complexionNameList = new ArrayList<>(), complexionIdList = new ArrayList<>(), ageList = new ArrayList<>(),
            maritalStatusNameList = new ArrayList<>(), maritalStatusIdList = new ArrayList<>(), seekingForMarriageNameList = new ArrayList<>(),
            seekingForMarriageIdList = new ArrayList<>(), physicalStatusNameList = new ArrayList<>(), physicalStatusIdList = new ArrayList<>(),
            monthlyIncomeNameList = new ArrayList<>(), monthlyIncomeIdList = new ArrayList<>(), typeOfIssueNameList = new ArrayList<>(),
            typeOfIssueIdList = new ArrayList<>();

    public void makeProfileForList(JsonList jsonList) {
        profileForNameList = new ArrayList<>();
        profileForIdList = new ArrayList<>();

        try {
            for (Json json : jsonList) {
                profileForNameList.add(json.getString(P.name).trim());

                profileForIdList.add(json.getString(P.id).trim());
            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    public void makeCityList(JsonList jsonList) {
        cityNameList = new ArrayList<>();
        cityIdList = new ArrayList<>();

        try {
            for (Json json : jsonList) {
                cityNameList.add(json.getString(P.city_name).trim());

                cityIdList.add(json.getString(P.city_id).trim());
            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    public void makeStateList(JsonList jsonList) {
        stateNameList = new ArrayList<>();
        stateIdList = new ArrayList<>();

        try {
            for (Json json : jsonList) {
                stateNameList.add(json.getString(P.state_name).trim());

                stateIdList.add(json.getString(P.state_id).trim());
            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    public void makeCountryList(JsonList jsonList) {
        countryNameList = new ArrayList<>();
        countryIdList = new ArrayList<>();
        countryCodeList = new ArrayList<>();

        try {
            for (Json json : jsonList) {
                countryNameList.add(json.getString(P.name).trim());

                countryIdList.add(json.getString(P.id).trim());

                countryCodeList.add(json.getString(P.country_code).trim());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void makeHeightList(JSONArray jsonArray) {
        heightList = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                heightList.add(jsonArray.getString(i).trim());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void makeReligionList(JsonList jsonList) {
        religionNameList = new ArrayList<>();
        religionIdList = new ArrayList<>();

       try {
           for (Json json : jsonList) {
               religionNameList.add(json.getString(P.name).trim());

               religionIdList.add(json.getString(P.id).trim());
           }
       }
       catch (NullPointerException e)
       {
           e.printStackTrace();
       }
    }

    public void makeEthnicityList(JsonList jsonList) {
        ethnicityNameList = new ArrayList<>();
        ethnicityIdList = new ArrayList<>();

        try {
            for (Json json : jsonList) {
                ethnicityNameList.add(json.getString(P.name).trim());

                ethnicityIdList.add(json.getString(P.id).trim());
            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    public void makeOccupationList(JsonList jsonList) {
        occupationNameList = new ArrayList<>();
        occupationIdList = new ArrayList<>();

        try {
            for (Json json : jsonList) {
                occupationNameList.add(json.getString(P.name).trim());

                occupationIdList.add(json.getString(P.id).trim());
            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    public void makeEducationList(JsonList jsonList) {
        educationNameList = new ArrayList<>();
        educationIdList = new ArrayList<>();

        try {
            for (Json json : jsonList) {
                educationNameList.add(json.getString(P.name).trim());

                educationIdList.add(json.getString(P.id).trim());
            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    public void makeMotherTongueList(JsonList jsonList) {
        languageNameList = new ArrayList<>();
        languageIdList = new ArrayList<>();

       try {
           for (Json json : jsonList) {
               languageNameList.add(json.getString(P.name).trim());

               languageIdList.add(json.getString(P.id).trim());
           }
       }
       catch (NullPointerException e)
       {
           e.printStackTrace();
       }
    }

    public void makeSmokingList(JsonList jsonList) {
        smokingNameList = new ArrayList<>();
        smokingIdList = new ArrayList<>();

       try {
           for (Json json : jsonList) {
               smokingNameList.add(json.getString(P.val).trim());

               smokingIdList.add(json.getString(P.id).trim());
           }
       }
       catch (NullPointerException e)
       {
           e.printStackTrace();
       }
    }

    public void makeRelocateList(JsonList jsonList) {
        relocateNameList = new ArrayList<>();
        relocateIdList = new ArrayList<>();

        try {
            for (Json json : jsonList) {
                relocateNameList.add(json.getString(P.val).trim());

                relocateIdList.add(json.getString(P.id).trim());
            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    public void makeInterestedInList(JsonList jsonList) {
        intrestedInNameList = new ArrayList<>();
        intrestedInIdList = new ArrayList<>();

        try {
            for (Json json : jsonList) {
                intrestedInNameList.add(json.getString(P.name).trim());

                intrestedInIdList.add(json.getString(P.id).trim());
            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    public void makeComplexionList(JsonList jsonList) {
        complexionNameList = new ArrayList<>();
        complexionIdList = new ArrayList<>();

        try {
            for (Json json : jsonList) {
                complexionNameList.add(json.getString(P.val).trim());

                complexionIdList.add(json.getString(P.id).trim());
            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    public void makeAgeList(JSONArray jsonArray) {
        ageList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                ageList.add(jsonArray.getString(i).trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void makeMaritalStatusList(JsonList jsonList) {
        maritalStatusNameList = new ArrayList<>();
        maritalStatusIdList = new ArrayList<>();

        try {
            for (Json json : jsonList) {
                maritalStatusNameList.add(json.getString(P.val).trim());

                maritalStatusIdList.add(json.getString(P.id).trim());
            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    public void makeSeekingForMarriageList(JsonList jsonList) {
        seekingForMarriageNameList = new ArrayList<>();
        seekingForMarriageIdList = new ArrayList<>();

        try {
            for (Json json : jsonList) {
                seekingForMarriageNameList.add(json.getString(P.val).trim());

                seekingForMarriageIdList.add(json.getString(P.id).trim());
            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    public void makePhysicalStatusList(JsonList jsonList) {
        physicalStatusNameList = new ArrayList<>();
        physicalStatusIdList = new ArrayList<>();

        try {
            for (Json json : jsonList) {
                physicalStatusNameList.add(json.getString(P.val).trim());

                physicalStatusIdList.add(json.getString(P.id).trim());
            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    public void makeMonthlyIncomeList(JsonList jsonList) {
        monthlyIncomeNameList = new ArrayList<>();
        monthlyIncomeIdList = new ArrayList<>();

        try {
            for (Json json : jsonList) {
                monthlyIncomeNameList.add(json.getString(P.name).trim());

                monthlyIncomeIdList.add(json.getString(P.id).trim());
            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    public void makeIssueList(JsonList jsonList) {
        typeOfIssueNameList = new ArrayList<>();
        typeOfIssueIdList = new ArrayList<>();

        try {
            for (Json json : jsonList) {
                typeOfIssueNameList.add(json.getString(P.name).trim());

                typeOfIssueIdList.add(json.getString(P.id).trim());
            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }
}
