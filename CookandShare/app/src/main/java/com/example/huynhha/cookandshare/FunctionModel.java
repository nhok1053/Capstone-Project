package com.example.huynhha.cookandshare;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class FunctionModel {
    //function in search activity
    public String covertStringToUnsigned(String str) {
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
        if (name.trim().length() == 0 || quality.trim().length() == 0) {
            return false;
        } else {
            return true;
        }
    }

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
}
