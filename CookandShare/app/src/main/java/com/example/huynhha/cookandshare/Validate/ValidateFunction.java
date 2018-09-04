package com.example.huynhha.cookandshare.Validate;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huynhha.cookandshare.R;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ValidateFunction {
    //function in search activity
    public String covertStringToUnsigneded(String str) {
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll("đ", "d");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //function in EditMaterialFragment
    public boolean checkEditEditTextMaterials(String name, String quality) {
        if (name.trim().length() == 0 || quality.trim().length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    //function in PostRecipeMaterialFragment
    public boolean checkInputMaterials(String name, String quality) {
        if (name.trim().length() == 0 || quality.trim().length() == 0 || quality.trim() == "0") {
            return false;
        } else {
            return true;
        }
    }

    //function in updateProfileFragment
    public boolean checkInputFirstNameText(String name) {
        if (!name.matches("^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯẠẢẤẦẨẪẬẮẰẲẴẶ" +
                "ẸẺẼỀẾỂưạảấầẩẫậắằẳẵặẹẻẽềếểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợ" +
                "ụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkInputFirstNameLength(String name) {
        if (name.length() > 30) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkInputMail(String mail) {
        if (!mail.matches("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkPhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("^[+]?[0-9]{10,13}$")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkInputDateOfBirth(String dateOfBirth) {
        if (dateOfBirth.length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    //function in post recipe
    public boolean checkInputNameOfRecipe(String name) {
        if (name.trim().length() == 0 || name.trim().length() > 50) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkInputDescriptionOfRecipe(String description) {
        if (description.trim().length() == 0 || description.trim().length() > 300) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkInputListCategoryOfRecipe(List categories) {
        if (categories.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkInputListMaterital(List materials) {
        if (materials.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkInputListStepOfRecipe(List steps) {
        if (steps.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkInputTotalTimeCook(String time) {
        if (time.equalsIgnoreCase("0 hour 0 minute") || time.equalsIgnoreCase("0p")) {
            return false;
        } else {
            return true;
        }
    }

    //function in postDetail
    //check input cookbook name
    public boolean checkInputCookbookNameCreateNewCookbook(String cookbookName) {
        if (cookbookName.trim().length() == 0 || cookbookName.trim().length() > 60) {
            return false;
        } else {
            return true;
        }
    }

    //check input cookbook description
    public boolean checkInputCookbookDescriptionCreateNewCookbook(String cookbookDes) {
        if (cookbookDes.trim().length() == 0 || cookbookDes.trim().length() > 200) {
            return false;
        } else {
            return true;
        }
    }

    //check dupplicate cookbook name
    public boolean checkInputCookbookNameDupplicate(String name1, String name2) {
        if (name1.trim().equalsIgnoreCase(name2.trim())) {
            return false;
        } else {
            return true;
        }
    }

    //function in cookbookInfoFragment
    //check input edit cookbook name
    public boolean checkInputEditCookbookName(String cookbookName) {
        if (cookbookName.trim().length() == 0 || cookbookName.trim().length() > 60) {
            return false;
        } else {
            return true;
        }
    }

    //check input edit cookbook description
    public boolean checkInputEditCookbookDescription(String cookbookDes) {
        if (cookbookDes.trim().length() == 0 || cookbookDes.trim().length() > 200) {
            return false;
        } else {
            return true;
        }
    }

    //check dupplicate edit cookbook name
    public boolean checkInputEditCookbookNameDupplicate(String name1, String name2) {
        if (name1.trim().equalsIgnoreCase(name2.trim())) {
            return false;
        } else {
            return true;
        }
    }

    //function in PostRecipeStepFragment
    public boolean checkPostStepDescription(String description) {
        if (description.trim().length() == 0 || description.trim().length() > 200) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkPostStepTime(String time){
        if (time.trim().length() == 0 || time.trim().length() > 20) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkPostStepTemperature(String temperature){
        if (temperature.trim().length() < 3 || temperature.trim().length() > 6) {
            return false;
        } else {
            return true;
        }
    }
}
