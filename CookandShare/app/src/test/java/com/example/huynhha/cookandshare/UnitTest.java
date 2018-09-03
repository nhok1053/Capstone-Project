package com.example.huynhha.cookandshare;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest {
    FunctionModel fm = new FunctionModel();

    @Test
    public void testInputSearchBarReturnLowerCaseStringValue() {
        String input = "ABC";
        String expectedValue = "abc";
        assertEquals(expectedValue, fm.covertStringToUnsigned(input));
    }

    @Test
    public void testInputSearchBarReturnLowerCaseStringValueContainVietnameseText() {
        String input = "Đang";
        String expectedValue = "dang";
        assertEquals(expectedValue, fm.covertStringToUnsigned(input));
    }

    @Test
    public void testEditMaterialNameAndQualityContainValue() {
        String inputName = "Cơm";
        String inputQuality = "12kg";
        boolean expected = true;
        assertEquals(expected, fm.checkEditEditTextMaterials(inputName, inputQuality));
    }

    @Test
    public void testEditMaterialNameAndQualityNotContainValueName() {
        String inputName = "";
        String inputQuality = "12kg";
        boolean expected = false;
        assertEquals(expected, fm.checkEditEditTextMaterials(inputName, inputQuality));
    }

    @Test
    public void testEditMaterialNameAndQualityNotContainValueQuality() {
        String inputName = "Cơm";
        String inputQuality = "";
        boolean expected = false;
        assertEquals(expected, fm.checkEditEditTextMaterials(inputName, inputQuality));
    }

    @Test
    public void testEditMaterialNameAndQualityNotContainValueNameAndQuality() {
        String inputName = "";
        String inputQuality = "";
        boolean expected = false;
        assertEquals(expected, fm.checkEditEditTextMaterials(inputName, inputQuality));
    }

    @Test
    public void testAddMaterialNameAndQualityContainValue() {
        String inputName = "Cơm";
        String inputQuality = "12kg";
        boolean expected = true;
        assertEquals(expected, fm.checkInputMaterials(inputName, inputQuality));
    }

    @Test
    public void testAddtMaterialNameAndQualityNotContainValueName() {
        String inputName = "";
        String inputQuality = "12kg";
        boolean expected = false;
        assertEquals(expected, fm.checkInputMaterials(inputName, inputQuality));
    }

    @Test
    public void testAddMaterialNameAndQualityNotContainValueQuality() {
        String inputName = "Cơm";
        String inputQuality = "";
        boolean expected = false;
        assertEquals(expected, fm.checkInputMaterials(inputName, inputQuality));
    }

    @Test
    public void testAddMaterialNameAndQualityNotContainValueNameAndQuality() {
        String inputName = "";
        String inputQuality = "";
        boolean expected = false;
        assertEquals(expected, fm.checkInputMaterials(inputName, inputQuality));
    }

    @Test
    public void inputFirstNameContainSpecialRegex() {
        String inputName = "!@#Tô Tiến Lực%^&™";
        boolean expected = false;
        assertEquals(expected, fm.checkInputFirstNameText(inputName));
    }

    @Test
    public void inputFirstNameContainNoSpecialRegex() {
        String inputName = "Tô Tiến Lực";
        boolean expected = true;
        assertEquals(expected, fm.checkInputFirstNameText(inputName));
    }

    @Test
    public void inputFirstNameLengthLessThan30() {
        String inputName = "Tô Tiến Lực";
        boolean expected = true;
        assertEquals(expected, fm.checkInputFirstNameLength(inputName));
    }

    @Test
    public void inputFirstNameLengthMoreThan30() {
        String inputName = "qwertyuiopasdfghjklzxcvbnmqwertyuiop";
        boolean expected = false;
        assertEquals(expected, fm.checkInputFirstNameLength(inputName));
    }

    @Test
    public void inputMailInvalid() {
        String inputMail = "lucttse04138";
        boolean expected = false;
        assertEquals(expected, fm.checkInputMail(inputMail));
    }

    @Test
    public void inputMailValid() {
        String inputMail = "lucttse04138@gmail.com";
        boolean expected = true;
        assertEquals(expected, fm.checkInputMail(inputMail));
    }

    @Test
    public void inputPhoneNumberLessThan10Regex() {
        String inputPhoneNumber = "012345";
        boolean expected = false;
        assertEquals(expected, fm.checkPhoneNumber(inputPhoneNumber));
    }

    @Test
    public void inputPhoneNumberMoreThan13Regex() {
        String inputPhoneNumber = "0123456789101112";
        boolean expected = false;
        assertEquals(expected, fm.checkPhoneNumber(inputPhoneNumber));
    }

    @Test
    public void inputPhoneNumberContainText() {
        String inputPhoneNumber = "0123ac1112";
        boolean expected = false;
        assertEquals(expected, fm.checkPhoneNumber(inputPhoneNumber));
    }

    @Test
    public void inputPhoneNumberContainSpecialRegex() {
        String inputPhoneNumber = "0123ac1%#2";
        boolean expected = false;
        assertEquals(expected, fm.checkPhoneNumber(inputPhoneNumber));
    }

    @Test
    public void inputValidPhoneNumber() {
        String inputPhoneNumber = "01664355754";
        boolean expected = true;
        assertEquals(expected, fm.checkPhoneNumber(inputPhoneNumber));
    }

    @Test
    public void inputValidDateOfBirth() {
        String inputDateOfBirth = "20/05/2000";
        boolean expected = true;
        assertEquals(expected, fm.checkInputDateOfBirth(inputDateOfBirth));
    }

    @Test
    public void inputInvalidDateOfBirth() {
        String inputDateOfBirth = "";
        boolean expected = false;
        assertEquals(expected, fm.checkInputDateOfBirth(inputDateOfBirth));
    }
}