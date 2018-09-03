package com.example.huynhha.cookandshare;

import com.example.huynhha.cookandshare.Validate.ValidateFunction;
import com.example.huynhha.cookandshare.entity.Category;
import com.example.huynhha.cookandshare.entity.Material;
import com.example.huynhha.cookandshare.entity.PostStep;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest {
    ValidateFunction vf = new ValidateFunction();

    //test function convertString To unsigned
    @Test
    public void testEquivalenceInputSearchBarAllUpperCase() {
        String input = "ABC";
        String expectedValue = "abc";
        assertEquals(expectedValue, vf.covertStringToUnsigned(input));
    }

    @Test
    public void testEquivalenceInputSearchBarSomeUpperCase() {
        String input = "AbC";
        String expectedValue = "abc";
        assertEquals(expectedValue, vf.covertStringToUnsigned(input));
    }

    @Test
    public void testEquivalenceInputSearchBarAllLowerCase() {
        String input = "abc";
        String expectedValue = "abc";
        assertEquals(expectedValue, vf.covertStringToUnsigned(input));
    }

    @Test
    public void testBoundaryInputSearchBarContainVietnameseTextWithAllLowerCase() {
        String input = "đang";
        String expectedValue = "dang";
        assertEquals(expectedValue, vf.covertStringToUnsigned(input));
    }

    @Test
    public void testBoundaryInputSearchBarContainVietnameseTextWithSomeUpperCase() {
        String input = "ĐanG";
        String expectedValue = "dang";
        assertEquals(expectedValue, vf.covertStringToUnsigned(input));
    }

    @Test
    public void testBoundaryInputSearchBarContainVietnameseTextWithAllUpperCase() {
        String input = "ĐANG";
        String expectedValue = "dang";
        assertEquals(expectedValue, vf.covertStringToUnsigned(input));
    }

    @Test
    public void testBoundaryInputSearchBarContainNoChar() {
        String input = "";
        String expectedValue = "";
        assertEquals(expectedValue, vf.covertStringToUnsigned(input));
    }

    //test input at edit material name and quality
    @Test
    public void testEquivalenceEditMaterialNameAndQualityContainValue() {
        String inputName = "Cơm";
        String inputQuality = "12kg";
        boolean expected = true;
        assertEquals(expected, vf.checkEditEditTextMaterials(inputName, inputQuality));
    }

    @Test
    public void testEquivalenceEditMaterialNameAndQualityNotContainValueName() {
        String inputName = "";
        String inputQuality = "12kg";
        boolean expected = false;
        assertEquals(expected, vf.checkEditEditTextMaterials(inputName, inputQuality));
    }

    @Test
    public void testEquivalenceEditMaterialNameAndQualityNotContainValueQuality() {
        String inputName = "Cơm";
        String inputQuality = "";
        boolean expected = false;
        assertEquals(expected, vf.checkEditEditTextMaterials(inputName, inputQuality));
    }

    @Test
    public void testEquivalenceEditMaterialNameAndQualityNotContainValueNameAndQuality() {
        String inputName = "";
        String inputQuality = "";
        boolean expected = false;
        assertEquals(expected, vf.checkEditEditTextMaterials(inputName, inputQuality));
    }

    @Test
    public void testEquivalenceAddMaterialNameAndQualityContainValue() {
        String inputName = "Cơm";
        String inputQuality = "12kg";
        boolean expected = true;
        assertEquals(expected, vf.checkInputMaterials(inputName, inputQuality));
    }

    @Test
    public void testEquivalenceAddtMaterialNameAndQualityNotContainValueName() {
        String inputName = "";
        String inputQuality = "12kg";
        boolean expected = false;
        assertEquals(expected, vf.checkInputMaterials(inputName, inputQuality));
    }

    @Test
    public void testEquivalenceAddMaterialNameAndQualityNotContainValueQuality() {
        String inputName = "Cơm";
        String inputQuality = "";
        boolean expected = false;
        assertEquals(expected, vf.checkInputMaterials(inputName, inputQuality));
    }

    @Test
    public void testEquivalenceAddMaterialNameAndQualityNotContainValueNameAndQuality() {
        String inputName = "";
        String inputQuality = "";
        boolean expected = false;
        assertEquals(expected, vf.checkInputMaterials(inputName, inputQuality));
    }

    @Test
    public void testBoundaryInputNameAndQualityAddMaterialContainValueNameAndQualityEqual0() {
        String inputName = "C";
        String inputQuality = "0";
        boolean expected = false;
        assertEquals(expected, vf.checkInputMaterials(inputName, inputQuality));
    }

    @Test
    public void testBoundaryInputNameAndQualityAddMaterialNoContainValueNameAndQualityEqual0() {
        String inputName = "C";
        String inputQuality = "0";
        boolean expected = false;
        assertEquals(expected, vf.checkInputMaterials(inputName, inputQuality));
    }

    @Test
    public void testBoundaryInputNameAndQualityAddMaterialContainValueNameAndQualityEqual1() {
        String inputName = "C";
        String inputQuality = "1";
        boolean expected = true;
        assertEquals(expected, vf.checkInputMaterials(inputName, inputQuality));
    }

    @Test
    public void testBoundaryInputNameAndQualityAddMaterialNoContainValueNameAndQualityEqual1() {
        String inputName = "";
        String inputQuality = "1";
        boolean expected = false;
        assertEquals(expected, vf.checkInputMaterials(inputName, inputQuality));
    }

    //test input User Name
    @Test
    public void testEquivalenceInputInvalidFirstNameNoContainChar() {
        String inputName = "";
        boolean expected = false;
        assertEquals(expected, vf.checkInputFirstNameText(inputName));
    }

    @Test
    public void testEquivalenceInputInvalidFirstNameContainSpecialChar() {
        String inputName = "!@#Tô Tiến Lực%^&™";
        boolean expected = false;
        assertEquals(expected, vf.checkInputFirstNameText(inputName));
    }

    @Test
    public void testEquivalenceInputValidFirstNameContainNoSpecialChar() {
        String inputName = "Tô Tiến Lực";
        boolean expected = true;
        assertEquals(expected, vf.checkInputFirstNameText(inputName));
    }

    @Test
    public void testEquivalenceInputValidFirstNameLengthLessThan30Char() {
        String inputName = "Tô Tiến Lực";
        boolean expected = true;
        assertEquals(expected, vf.checkInputFirstNameLength(inputName));
    }

    @Test
    public void testBoundaryInputValidFirstNameLengthMoreThan30() {
        String inputName = "qwertyuiopasdfghjklzxcvbnmqwertyuiop";
        boolean expected = false;
        assertEquals(expected, vf.checkInputFirstNameLength(inputName));
    }

    @Test
    public void testBoundaryInputValidFirstNameLength30() {
        String inputName = "qwertyuiopasdfghjklzxcvbnmqwer";
        boolean expected = true;
        assertEquals(expected, vf.checkInputFirstNameLength(inputName));
    }

    @Test
    public void testBoundaryInputValidFirstNameLength1() {
        String inputName = "q";
        boolean expected = true;
        assertEquals(expected, vf.checkInputFirstNameLength(inputName));
    }

    @Test
    public void testBoundaryInputInValidFirstNameLength31() {
        String inputName = "qwertyuiopasdfghjklzxcvbnmqwert";
        boolean expected = false;
        assertEquals(expected, vf.checkInputFirstNameLength(inputName));
    }

    //test input email
    @Test
    public void testEquivalenceInputInvalidMailNoContainChar() {
        String inputMail = "";
        boolean expected = false;
        assertEquals(expected, vf.checkInputMail(inputMail));
    }

    @Test
    public void testEquivalenceInputInvalidMailContainNoMailMark() {
        String inputMail = "lucttse04138";
        boolean expected = false;
        assertEquals(expected, vf.checkInputMail(inputMail));
    }

    @Test
    public void testEquivalenceInputValidMailContainMailMark() {
        String inputMail = "lucttse04138@gmail.com";
        boolean expected = true;
        assertEquals(expected, vf.checkInputMail(inputMail));
    }

    @Test
    public void testBoundaryInputValidMailContainMailMark() {
        String inputMail = "l@g.c";
        boolean expected = true;
        assertEquals(expected, vf.checkInputMail(inputMail));
    }

    @Test
    public void testBoundaryInputValidMailContainNoMailMark() {
        String inputMail = "l";
        boolean expected = false;
        assertEquals(expected, vf.checkInputMail(inputMail));
    }

    @Test
    public void testBoundaryInputValidMailContainMailMarkNotEnough() {
        String inputMail = "l@";
        boolean expected = false;
        assertEquals(expected, vf.checkInputMail(inputMail));
    }

    //test input phone number
    @Test
    public void testEquivalenceInputPhoneNumberInvalidLessThanStandardDigit() {
        String inputPhoneNumber = "012345";
        boolean expected = false;
        assertEquals(expected, vf.checkPhoneNumber(inputPhoneNumber));
    }

    @Test
    public void testEquivalenceInputPhoneNumberValid() {
        String inputPhoneNumber = "012345678910";
        boolean expected = true;
        assertEquals(expected, vf.checkPhoneNumber(inputPhoneNumber));
    }

    @Test
    public void testEquivalenceInputPhoneNumberInvalidMoreThanStandardDigit() {
        String inputPhoneNumber = "0123456789101112";
        boolean expected = false;
        assertEquals(expected, vf.checkPhoneNumber(inputPhoneNumber));
    }

    @Test
    public void testBoundaryInputPhoneNumberInvalidContainText() {
        String inputPhoneNumber = "abcabcabcde";
        boolean expected = false;
        assertEquals(expected, vf.checkPhoneNumber(inputPhoneNumber));
    }

    @Test
    public void testBoundaryInputPhoneNumberInvalidContainSpecialChar() {
        String inputPhoneNumber = "0123ac1%#2";
        boolean expected = false;
        assertEquals(expected, vf.checkPhoneNumber(inputPhoneNumber));
    }

    @Test
    public void testBoundaryInputValidPhoneNumber10digit() {
        String inputPhoneNumber = "0166435575";
        boolean expected = true;
        assertEquals(expected, vf.checkPhoneNumber(inputPhoneNumber));
    }

    @Test
    public void testBoundaryInputValidPhoneNumber13digit() {
        String inputPhoneNumber = "0166435575412";
        boolean expected = true;
        assertEquals(expected, vf.checkPhoneNumber(inputPhoneNumber));
    }

    @Test
    public void testBoundaryInputValidPhoneNumber11digit() {
        String inputPhoneNumber = "01664355754";
        boolean expected = true;
        assertEquals(expected, vf.checkPhoneNumber(inputPhoneNumber));
    }

    @Test
    public void testBoundaryInputInvalidPhoneNumber14digit() {
        String inputPhoneNumber = "01664355754124";
        boolean expected = false;
        assertEquals(expected, vf.checkPhoneNumber(inputPhoneNumber));
    }

    @Test
    public void testBoundaryInputInvalidPhoneNumber9digit() {
        String inputPhoneNumber = "016643557";
        boolean expected = false;
        assertEquals(expected, vf.checkPhoneNumber(inputPhoneNumber));
    }

    //test input date of birth
    @Test
    public void testEquivalenceInputValidDateOfBirth() {
        String inputDateOfBirth = "20/05/2000";
        boolean expected = true;
        assertEquals(expected, vf.checkInputDateOfBirth(inputDateOfBirth));
    }

    @Test
    public void testEquivalenceInputInvalidDateOfBirth() {
        String inputDateOfBirth = "";
        boolean expected = false;
        assertEquals(expected, vf.checkInputDateOfBirth(inputDateOfBirth));
    }

    @Test
    public void testBoundaryInputValidDateOfBirthContain1Char() {
        String inputDateOfBirth = "2";
        boolean expected = true;
        assertEquals(expected, vf.checkInputDateOfBirth(inputDateOfBirth));
    }

    @Test
    public void testBoundaryInputInvalidDateOfBirthNoContainChar() {
        String inputDateOfBirth = "";
        boolean expected = false;
        assertEquals(expected, vf.checkInputDateOfBirth(inputDateOfBirth));
    }

    //test input name of recipe
    @Test
    public void testEquivalenceInputInvalidNameOfRecipeNoContainChar() {
        String inputRecipeName = "";
        boolean expected = false;
        assertEquals(expected, vf.checkInputNameOfRecipe(inputRecipeName));
    }

    @Test
    public void testEquivalenceInputInvalidNameOfRecipeContainMoreThan50Char() {
        String inputRecipeName = "Cách làm bánh tart phô mai Hokkaido - Hokkaido baked cheese tart - What is WordCounter";
        boolean expected = false;
        assertEquals(expected, vf.checkInputNameOfRecipe(inputRecipeName));
    }

    @Test
    public void testEquivalenceInputValidNameOfRecipe() {
        String inputRecipeName = "Cách làm bánh tart phô mai Hokkaido";
        boolean expected = true;
        assertEquals(expected, vf.checkInputNameOfRecipe(inputRecipeName));
    }

    @Test
    public void testBoundaryInputValidNameOfRecipeContain0Char() {
        String inputRecipeName = "";
        boolean expected = false;
        assertEquals(expected, vf.checkInputNameOfRecipe(inputRecipeName));
    }

    @Test
    public void testBoundaryInputValidNameOfRecipeContain1Char() {
        String inputRecipeName = "C";
        boolean expected = true;
        assertEquals(expected, vf.checkInputNameOfRecipe(inputRecipeName));
    }

    @Test
    public void testBoundaryInputValidNameOfRecipeContain50Char() {
        String inputRecipeName = "Cách làm bánh tart phô mai Hokkaido baked cheese t";
        boolean expected = true;
        assertEquals(expected, vf.checkInputNameOfRecipe(inputRecipeName));
    }

    @Test
    public void testBoundaryInputValidNameOfRecipeContain51Char() {
        String inputRecipeName = "Cách làm bánh tart phô mai Hokkaido baked cheese ta";
        boolean expected = false;
        assertEquals(expected, vf.checkInputNameOfRecipe(inputRecipeName));
    }


    //test input description of recipe
    @Test
    public void testEquivalenceInputInvalidDescriptionOfRecipeLessThanStandard() {
        String inputRecipeDescription = "";
        boolean expected = false;
        assertEquals(expected, vf.checkInputDescriptionOfRecipe(inputRecipeDescription));
    }

    @Test
    public void testEquivalenceInputInvalidDescriptionOfRecipeMoreThanStandard() {
        String inputRecipeDescription = "Cách làm bánh tart phô mai Hokkaido cũng như cách làm bánh tart trứng," +
                " hay cách làm bánh tart phô mai bình thường nhưng lại có một chút biến tấu trong nguyên liệu" +
                " và cách chế biến, tuy chỉ là biến tấu nhỏ nhưng lại giúp món bánh tart phô mai" +
                " Hokkaido ngon lành và hấp dẫn hơn rất nhiều. Nếu bạn là một người yêu thích món ăn này," +
                " hay chỉ là yêu thích đất nước Nhật Bản thì đừng bỏ qua món này nha.";
        boolean expected = false;
        assertEquals(expected, vf.checkInputDescriptionOfRecipe(inputRecipeDescription));
    }

    @Test
    public void testEquivalenceInputValidDescriptionOfRecipe() {
        String inputRecipeDescription = "Cách làm bánh tart phô mai Hokkaido cũng như cách làm bánh tart trứng," +
                " hay cách làm bánh tart phô mai bình thường nhưng lại có một chút biến tấu";
        boolean expected = true;
        assertEquals(expected, vf.checkInputDescriptionOfRecipe(inputRecipeDescription));
    }

    @Test
    public void testBoundaryInputInvalidDescriptionOfRecipeContain0Char() {
        String inputRecipeDescription = "";
        boolean expected = false;
        assertEquals(expected, vf.checkInputDescriptionOfRecipe(inputRecipeDescription));
    }

    @Test
    public void testBoundaryInputValidDescriptionOfRecipeyContain1Char() {
        String inputRecipeDescription = "C";
        boolean expected = true;
        assertEquals(expected, vf.checkInputDescriptionOfRecipe(inputRecipeDescription));
    }

    @Test
    public void testBoundaryInputValidDescriptionOfRecipeContain300Char() {
        String inputRecipeDescription = "Cách làm bánh tart phô mai Hokkaido cũng như cách làm bánh tart trứng, " +
                "hay cách làm bánh tart phô mai bình thường nhưng lại có một chút biến tấu trong nguyên liệu và " +
                "cách chế biến, tuy chỉ là biến tấu nhỏ nhưng lại giúp món bánh tart phô mai Hokkaido ngon lành và " +
                "hấp dẫn hơn rất nhiều. Nếu bạn là mộ";
        boolean expected = true;
        assertEquals(expected, vf.checkInputDescriptionOfRecipe(inputRecipeDescription));
    }

    @Test
    public void testBoundaryInputInvalidDescriptionOfRecipeContain301Char() {
        String inputRecipeDescription = "Cách làm bánh tart phô mai Hokkaido cũng như cách làm bánh tart trứng," +
                " hay cách làm bánh tart phô mai bình thường nhưng lại có một chút biến tấu trong nguyên" +
                " liệu và cách chế biến, tuy chỉ là biến tấu nhỏ nhưng lại giúp món bánh tart phô mai Hokkaido" +
                " ngon lành và hấp dẫn hơn rất nhiều. Nếu bạn là một";
        boolean expected = false;
        assertEquals(expected, vf.checkInputDescriptionOfRecipe(inputRecipeDescription));
    }

    //test input checked list category
    @Test
    public void testEquivalenceInputInvalidListCategories() {
        List<Category> categories = new ArrayList<>();
        boolean expected = false;
        assertEquals(expected, vf.checkInputListCategoryOfRecipe(categories));
    }

    @Test
    public void testEquivalenceInputValidListCategories() {
        Category category1 = new Category(1, "Bữa sáng", "breakfast.jpg");
        Category category2 = new Category(2, "Tốt cho sức khỏe", "healthy.jpg");
        Category category3 = new Category(6, "Phở, mỳ, bún", "noodle.jpg");
        List<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        boolean expected = true;
        assertEquals(expected, vf.checkInputListCategoryOfRecipe(categories));
    }

    @Test
    public void testBoundaryInputValidListCategoriesListContain1Object() {
        Category category1 = new Category(1, "Bữa sáng", "breakfast.jpg");
        List<Category> categories = new ArrayList<>();
        categories.add(category1);
        boolean expected = true;
        assertEquals(expected, vf.checkInputListCategoryOfRecipe(categories));
    }

    @Test
    public void testBoundaryInputInvalidListCategoriesListContain0Object() {
        List<Category> categories = new ArrayList<>();
        boolean expected = false;
        assertEquals(expected, vf.checkInputListCategoryOfRecipe(categories));
    }

    //test input checked list material
    @Test
    public void testEquivalenceInputInvalidListMaterials() {
        List<Material> materials = new ArrayList<>();
        boolean expected = false;
        assertEquals(expected, vf.checkInputListMaterital(materials));
    }

    @Test
    public void testEquivalenceInputValidListMaterials() {
        Material material1 = new Material("mt1", "Cá", "1.5", "kg");
        Material material2 = new Material("mt2", "Rau thì là", "2", "bó");
        Material material3 = new Material("mt3", "Cà chua", "3", "quả");
        List<Material> materials = new ArrayList<>();
        materials.add(material1);
        materials.add(material2);
        materials.add(material3);
        boolean expected = true;
        assertEquals(expected, vf.checkInputListMaterital(materials));
    }

    @Test
    public void testBoundaryInputValidListMaterialsListContain1Object() {
        Material material1 = new Material("mt1", "Cá", "1.5", "kg");
        List<Material> materials = new ArrayList<>();
        materials.add(material1);
        boolean expected = true;
        assertEquals(expected, vf.checkInputListMaterital(materials));
    }

    @Test
    public void testBoundaryInputInvalidListMaterialsListContain0Object() {
        List<Material> materials = new ArrayList<>();
        boolean expected = false;
        assertEquals(expected, vf.checkInputListMaterital(materials));
    }

    //test input checked list post step
    @Test
    public void testEquivalenceInputInvalidListSteps() {
        List<PostStep> postSteps = new ArrayList<>();
        boolean expected = false;
        assertEquals(expected, vf.checkInputListStepOfRecipe(postSteps));
    }

    @Test
    public void testEquivalenceInputValidListSteps() {
        PostStep postStep1 = new PostStep("uri", "1.jpg", "2", "description", "15*C", "15m");
        PostStep postStep2 = new PostStep("uri", "2.jpg", "3", "description", "17*C", "25m");
        PostStep postStep3 = new PostStep("uri", "3.jpg", "4", "description", "11*C", "35m");
        List<PostStep> postSteps = new ArrayList<>();
        postSteps.add(postStep1);
        postSteps.add(postStep2);
        postSteps.add(postStep3);
        boolean expected = true;
        assertEquals(expected, vf.checkInputListStepOfRecipe(postSteps));
    }

    @Test
    public void testBoundaryInputValidListStepsListContain1Object() {
        PostStep postStep1 = new PostStep("uri", "1.jpg", "2", "description", "11*C", "35m");
        List<PostStep> postSteps = new ArrayList<>();
        postSteps.add(postStep1);
        boolean expected = true;
        assertEquals(expected, vf.checkInputListStepOfRecipe(postSteps));
    }

    @Test
    public void testBoundaryInputInvalidListStepsListContain0Object() {
        List<PostStep> postSteps = new ArrayList<>();
        boolean expected = false;
        assertEquals(expected, vf.checkInputListStepOfRecipe(postSteps));
    }

    //test input total time cook
    @Test
    public void testEquivalenceInputValidTotalTimeCook() {
        String inputTotalTime = "10 hour 50 minute";
        boolean expected = true;
        assertEquals(expected, vf.checkInputTotalTimeCook(inputTotalTime));
    }

    @Test
    public void testEquivalenceInputInvalidTotalTimeCookContain0Minute() {
        String inputTotalTime = "0p";
        boolean expected = false;
        assertEquals(expected, vf.checkInputTotalTimeCook(inputTotalTime));
    }

    @Test
    public void testEquivalenceInputInvalidTotalTimeCookContain0MinuteUpperCase() {
        String inputTotalTime = "0P";
        boolean expected = false;
        assertEquals(expected, vf.checkInputTotalTimeCook(inputTotalTime));
    }

    @Test
    public void testEquivalenceInputInvalidTotalTimeCookeContain0Hour0Minute() {
        String inputTotalTime = "0 hour 0 minute";
        boolean expected = false;
        assertEquals(expected, vf.checkInputTotalTimeCook(inputTotalTime));
    }

    @Test
    public void testEquivalenceInputInvalidTotalTimeCookContain0Hour0MinuteAllUpperCase() {
        String inputTotalTime = "0 HOUR 0 MINUTE";
        boolean expected = false;
        assertEquals(expected, vf.checkInputTotalTimeCook(inputTotalTime));
    }

    @Test
    public void testEquivalenceInputInvalidTotalTimeCookContain0Hour0MinuteSomeUpperCase() {
        String inputTotalTime = "0 Hour 0 Minute";
        boolean expected = false;
        assertEquals(expected, vf.checkInputTotalTimeCook(inputTotalTime));
    }

    @Test
    public void testBoundaryInputInvalidTotalTimeCookyContain0Hour0MinuteSomeUpperCase() {
        String inputTotalTime = "0 Hour 0 Minute";
        boolean expected = false;
        assertEquals(expected, vf.checkInputTotalTimeCook(inputTotalTime));
    }

    @Test
    public void testBoundaryInputInvalidTotalTimeCookContain0Hour0MinuteAllUpperCase() {
        String inputTotalTime = "0 HOUR 0 MINUTE";
        boolean expected = false;
        assertEquals(expected, vf.checkInputTotalTimeCook(inputTotalTime));
    }

    @Test
    public void testBoundaryInputInvalidTotalTimeCookContain0Hour0MinuteNoUpperCase() {
        String inputTotalTime = "0 hour 0 minute";
        boolean expected = false;
        assertEquals(expected, vf.checkInputTotalTimeCook(inputTotalTime));
    }

    @Test
    public void testBoundaryInputValidTotalTimeCookContain0HourAnd1MinuteNoUpperCase() {
        String inputTotalTime = "0 hour 1 minute";
        boolean expected = true;
        assertEquals(expected, vf.checkInputTotalTimeCook(inputTotalTime));
    }

    @Test
    public void testBoundaryInputValidTotalTimeCookContain0HourAnd1MinuteSomeUpperCase() {
        String inputTotalTime = "0 Hour 1 Minute";
        boolean expected = true;
        assertEquals(expected, vf.checkInputTotalTimeCook(inputTotalTime));
    }

    @Test
    public void testBoundaryInputValidTotalTimeCookContain0HourAnd1MinuteAllUpperCase() {
        String inputTotalTime = "0 HOUR 1 MINUTE";
        boolean expected = true;
        assertEquals(expected, vf.checkInputTotalTimeCook(inputTotalTime));
    }

    @Test
    public void testBoundaryInputValidTotalTimeCookContain1HourAnd0MinuteNoUpperCase() {
        String inputTotalTime = "1 hour 0 minute";
        boolean expected = true;
        assertEquals(expected, vf.checkInputTotalTimeCook(inputTotalTime));
    }

    @Test
    public void testBoundaryInputValidTotalTimeCookContain1HourAnd0MinuteSomeUpperCase() {
        String inputTotalTime = "1 Hour 0 Minute";
        boolean expected = true;
        assertEquals(expected, vf.checkInputTotalTimeCook(inputTotalTime));
    }

    @Test
    public void testBoundaryInputValidTotalTimeCookContain1HourAnd0MinuteAllUpperCase() {
        String inputTotalTime = "1 HOUR 0 MINUTE";
        boolean expected = true;
        assertEquals(expected, vf.checkInputTotalTimeCook(inputTotalTime));
    }

    @Test
    public void testBoundaryInputValidTotalTimeCookContain1MinuteAllUpperCase() {
        String inputTotalTime = "1P";
        boolean expected = true;
        assertEquals(expected, vf.checkInputTotalTimeCook(inputTotalTime));
    }

    @Test
    public void testBoundaryInputValidTotalTimeCookContain1MinuteAllLowerCase() {
        String inputTotalTime = "1p";
        boolean expected = true;
        assertEquals(expected, vf.checkInputTotalTimeCook(inputTotalTime));
    }

    @Test
    public void testBoundaryInputValidTotalTimeCookContain59MinuteAllLowerCase() {
        String inputTotalTime = "59p";
        boolean expected = true;
        assertEquals(expected, vf.checkInputTotalTimeCook(inputTotalTime));
    }

    @Test
    public void testBoundaryInputValidTotalTimeCookContain59MinuteAllUpperCase() {
        String inputTotalTime = "59P";
        boolean expected = true;
        assertEquals(expected, vf.checkInputTotalTimeCook(inputTotalTime));
    }

    @Test
    public void testBoundaryInputValidTotalTimeCookContain23HourAnd59MinuteAllUpperCase() {
        String inputTotalTime = "23 HOUR 59 MINUTE";
        boolean expected = true;
        assertEquals(expected, vf.checkInputTotalTimeCook(inputTotalTime));
    }

    @Test
    public void testBoundaryInputValidTotalTimeCookContain23HourAnd59MinuteAllLowerCase() {
        String inputTotalTime = "23 hour 59 minute";
        boolean expected = true;
        assertEquals(expected, vf.checkInputTotalTimeCook(inputTotalTime));
    }

    @Test
    public void testBoundaryInputValidTotalTimeCookContain23HourAnd59MinuteSomeUpperCase() {
        String inputTotalTime = "23 Hour 59 Minute";
        boolean expected = true;
        assertEquals(expected, vf.checkInputTotalTimeCook(inputTotalTime));
    }

    //test input cookbookName when edit cookbook
    @Test
    public void testEquivalenceInputValidEditCookbookCookbookName() {
        String inputEditCookbookName = "Thứ 7 máu chảy về tim";
        boolean expected = true;
        assertEquals(expected, vf.checkInputEditCookbookName(inputEditCookbookName));
    }

    @Test
    public void testEquivalenceInputInvalidEditCookbookCookbookNameMoreThanStandard() {
        String inputEditCookbookName = "Thứ 7 máu chảy về tim, chủ nhật là ngày được nghỉ thứ 2 lại phải đi làm";
        boolean expected = false;
        assertEquals(expected, vf.checkInputEditCookbookName(inputEditCookbookName));
    }

    @Test
    public void testEquivalenceInputInvalidEditCookbookCookbookNameLessThanStandard() {
        String inputEditCookbookName = "";
        boolean expected = false;
        assertEquals(expected, vf.checkInputEditCookbookName(inputEditCookbookName));
    }

    @Test
    public void testBoundaryInputInvalidEditCookbookCookbookNameContain0Char() {
        String inputEditCookbookName = "";
        boolean expected = false;
        assertEquals(expected, vf.checkInputEditCookbookName(inputEditCookbookName));
    }

    @Test
    public void testBoundaryInputInvalidEditCookbookCookbookNameContain61Char() {
        String inputEditCookbookName = "Thứ 7 máu chảy về tim,chủ nhật là ngày được nghỉ thứ 2 lại ph";
        boolean expected = false;
        assertEquals(expected, vf.checkInputEditCookbookName(inputEditCookbookName));
    }

    @Test
    public void testBoundaryInputValidEditCookbookCookbookNameContain60Char() {
        String inputEditCookbookName = "Thứ 7 máu chảy về tim,chủ nhật là ngày được nghỉ thứ 2 lại p";
        boolean expected = true;
        assertEquals(expected, vf.checkInputEditCookbookName(inputEditCookbookName));
    }

    @Test
    public void testBoundaryInputValidEditCookbookCookbookNameContain1Char() {
        String inputEditCookbookName = "T";
        boolean expected = true;
        assertEquals(expected, vf.checkInputEditCookbookName(inputEditCookbookName));
    }

    //test input cookbook description when create cookbookllllllllllll
    @Test
    public void testEquivalenceInputValidEditCookbookCookbookDescription() {
        String inputEditCookbookName = "Mùa World Cup đã đến và cùng đang quá trình chuẩn bị. Sao nữa nhỉ ?";
        boolean expected = true;
        assertEquals(expected, vf.checkInputEditCookbookDescription(inputEditCookbookName));
    }

    @Test
    public void testEquivalenceInputInvalidEditCookbookCookbookDescriptionMoreThanStandard() {
        String inputEditCookbookName = "Mùa World Cup đã đến và cùng đang quá trình chuẩn bị. " +
                "Sao nữa nhỉ ? Tất nhiên sao gì nữa hòa cùng không khí đang nóng bừng này lên đi! " +
                "Nhâm nhi vài món cùng gia đình và chiến hữu thưởng thức khung cầu tuyệt tác nào";
        boolean expected = false;
        assertEquals(expected, vf.checkInputEditCookbookDescription(inputEditCookbookName));
    }

    @Test
    public void testEquivalenceInputInvalidEditCookbookCookbookDescriptionLessThanStandard() {
        String inputEditCookbookName = "";
        boolean expected = false;
        assertEquals(expected, vf.checkInputEditCookbookDescription(inputEditCookbookName));
    }

    @Test
    public void testBoundaryInputValidEditCookbookCookbookDescriptioContain1Char() {
        String inputEditCookbookName = "M";
        boolean expected = true;
        assertEquals(expected, vf.checkInputEditCookbookDescription(inputEditCookbookName));
    }

    @Test
    public void testBoundaryInputInvalidEditCookbookCookbookDescriptioContain0Char() {
        String inputEditCookbookName = "";
        boolean expected = false;
        assertEquals(expected, vf.checkInputEditCookbookDescription(inputEditCookbookName));
    }

    @Test
    public void testBoundaryInputInvalidEditCookbookCookbookDescriptioContain201Char() {
        String inputEditCookbookName = "Mùa World Cup đã đến và cùng đang quá trình chuẩn bị. " +
                "Sao nữa nhỉ? Tất nhiên sao gì nữa hòa cùng không khí đang nóng bừng này lên đi " +
                "Nhâm nhi vài món cùng gia đình và chiến hữu thưởng thức khung cầu ta";
        boolean expected = false;
        assertEquals(expected, vf.checkInputEditCookbookDescription(inputEditCookbookName));
    }

    @Test
    public void testBoundaryInputValidEditCookbookCookbookDescriptioContain200Char() {
        String inputEditCookbookName = "Mùa World Cup đã đến và cùng đang quá trình chuẩn bị. " +
                "Sao nữa nhỉ? Tất nhiên sao gì nữa hòa cùng không khí đang nóng bừng này lên đi " +
                "Nhâm nhi vài món cùng gia đình và chiến hữu thưởng thức khung cầu t";
        boolean expected = true;
        assertEquals(expected, vf.checkInputEditCookbookDescription(inputEditCookbookName));
    }

    //test duppicate name of cookbook
    @Test
    public void testEquivalentInputInvalidEditCookbookNameDupplicate2NameSame() {
        String inputEditCookbookName1 = "Món ăn ngày hè";
        String inputEditCookbookName2 = "Món ăn ngày hè";
        boolean expected = false;
        assertEquals(expected, vf.checkInputEditCookbookNameDupplicate(inputEditCookbookName1, inputEditCookbookName2));
    }

    @Test
    public void testEquivalentInputInvalidEditCookbookNameDupplicate2NameSameIgnorCase() {
        String inputEditCookbookName1 = "món ăn ngày Hè";
        String inputEditCookbookName2 = "Món ăn ngày hè";
        boolean expected = false;
        assertEquals(expected, vf.checkInputEditCookbookNameDupplicate(inputEditCookbookName1, inputEditCookbookName2));
    }

    @Test
    public void testEquivalentInputValidEditCookbookNameDupplicate2NameNotSameLowerCase() {
        String inputEditCookbookName1 = "món ăn ngày đông lạnh giá";
        String inputEditCookbookName2 = "món ăn ngày hè";
        boolean expected = true;
        assertEquals(expected, vf.checkInputEditCookbookNameDupplicate(inputEditCookbookName1, inputEditCookbookName2));
    }

    @Test
    public void testEquivalentInputValidEditCookbookNameDupplicate2NameNotSameSomeUpperCase() {
        String inputEditCookbookName1 = "Món ăn ngày đông lạnh giá";
        String inputEditCookbookName2 = "Món ăn ngày hè";
        boolean expected = true;
        assertEquals(expected, vf.checkInputEditCookbookNameDupplicate(inputEditCookbookName1, inputEditCookbookName2));
    }

    @Test
    public void testBoundaryInputInvalidEditCookbookNameDupplicate2NameSameLowerCase() {
        String inputEditCookbookName1 = "abc";
        String inputEditCookbookName2 = "abc";
        boolean expected = false;
        assertEquals(expected, vf.checkInputEditCookbookNameDupplicate(inputEditCookbookName1, inputEditCookbookName2));
    }

    @Test
    public void testBoundaryInputInvalidEditCookbookNameDupplicate2NameSameUpperCase() {
        String inputEditCookbookName1 = "ABC";
        String inputEditCookbookName2 = "ABC";
        boolean expected = false;
        assertEquals(expected, vf.checkInputEditCookbookNameDupplicate(inputEditCookbookName1, inputEditCookbookName2));
    }

    @Test
    public void testBoundaryInputInvalidEditCookbookNameDupplicate2NameSameSomeUpperCase() {
        String inputEditCookbookName1 = "AbC";
        String inputEditCookbookName2 = "AbC";
        boolean expected = false;
        assertEquals(expected, vf.checkInputEditCookbookNameDupplicate(inputEditCookbookName1, inputEditCookbookName2));
    }

    @Test
    public void testBoundaryInputValidEditCookbookNameDupplicate2NameNotSameSomeUpperCase() {
        String inputEditCookbookName1 = "AbCd";
        String inputEditCookbookName2 = "AbC";
        boolean expected = true;
        assertEquals(expected, vf.checkInputEditCookbookNameDupplicate(inputEditCookbookName1, inputEditCookbookName2));
    }

    @Test
    public void testBoundaryInputValidEditCookbookNameDupplicate2NameNotSameAllUpperCase() {
        String inputEditCookbookName1 = "ABCD";
        String inputEditCookbookName2 = "ABC";
        boolean expected = true;
        assertEquals(expected, vf.checkInputEditCookbookNameDupplicate(inputEditCookbookName1, inputEditCookbookName2));
    }

    @Test
    public void testBoundaryInputValidEditCookbookNameDupplicate2NameNotSameAllLowerCase() {
        String inputEditCookbookName1 = "abcd";
        String inputEditCookbookName2 = "abc";
        boolean expected = true;
        assertEquals(expected, vf.checkInputEditCookbookNameDupplicate(inputEditCookbookName1, inputEditCookbookName2));
    }

    //test input cookbookName when create cookbook
    @Test
    public void testEquivalenceInputValidCreateCookbookCookbookName() {
        String inputCookbookName = "Thứ 7 máu chảy về tim";
        boolean expected = true;
        assertEquals(expected, vf.checkInputCookbookNameCreateNewCookbook(inputCookbookName));
    }

    @Test
    public void testEquivalenceInputInvalidCreateCookbookCookbookNameMoreThanStandard() {
        String inputCookbookName = "Thứ 7 máu chảy về tim, chủ nhật là ngày được nghỉ thứ 2 lại phải đi làm";
        boolean expected = false;
        assertEquals(expected, vf.checkInputCookbookNameCreateNewCookbook(inputCookbookName));
    }

    @Test
    public void testEquivalenceInputInvalidCreateCookbookCookbookNameLessThanStandard() {
        String inputCookbookName = "";
        boolean expected = false;
        assertEquals(expected, vf.checkInputCookbookNameCreateNewCookbook(inputCookbookName));
    }

    @Test
    public void testBoundaryInputInvalidCreateCookbookCookbookNameContain0Char() {
        String inputCookbookName = "";
        boolean expected = false;
        assertEquals(expected, vf.checkInputCookbookNameCreateNewCookbook(inputCookbookName));
    }

    @Test
    public void testBoundaryInputInvalidCreateCookbookCookbookNameContain61Char() {
        String inputCookbookName = "Thứ 7 máu chảy về tim,chủ nhật là ngày được nghỉ thứ 2 lại ph";
        boolean expected = false;
        assertEquals(expected, vf.checkInputCookbookNameCreateNewCookbook(inputCookbookName));
    }

    @Test
    public void testBoundaryInputValidCreateCookbookCookbookNameContain60Char() {
        String inputCookbookName = "Thứ 7 máu chảy về tim,chủ nhật là ngày được nghỉ thứ 2 lại p";
        boolean expected = true;
        assertEquals(expected, vf.checkInputCookbookNameCreateNewCookbook(inputCookbookName));
    }

    @Test
    public void testBoundaryInputValidCreateCookbookCookbookNameContain1Char() {
        String inputCookbookName = "T";
        boolean expected = true;
        assertEquals(expected, vf.checkInputCookbookNameCreateNewCookbook(inputCookbookName));
    }

    //test input cookbook description when create cookbook
    @Test
    public void testEquivalenceInputValidCreateCookbookCookbookDescription() {
        String inputCookbookDes = "Mùa World Cup đã đến và cùng đang quá trình chuẩn bị. Sao nữa nhỉ ?";
        boolean expected = true;
        assertEquals(expected, vf.checkInputCookbookDescriptionCreateNewCookbook(inputCookbookDes));
    }

    @Test
    public void testEquivalenceInputInvalidCreateCookbookCookbookDescriptionMoreThanStandard() {
        String inputCookbookDes = "Mùa World Cup đã đến và cùng đang quá trình chuẩn bị. " +
                "Sao nữa nhỉ ? Tất nhiên sao gì nữa hòa cùng không khí đang nóng bừng này lên đi! " +
                "Nhâm nhi vài món cùng gia đình và chiến hữu thưởng thức khung cầu tuyệt tác nào";
        boolean expected = false;
        assertEquals(expected, vf.checkInputCookbookDescriptionCreateNewCookbook(inputCookbookDes));
    }

    @Test
    public void testEquivalenceInputInvalidCreateCookbookCookbookDescriptionLessThanStandard() {
        String inputCookbookDes = "";
        boolean expected = false;
        assertEquals(expected, vf.checkInputCookbookDescriptionCreateNewCookbook(inputCookbookDes));
    }

    @Test
    public void testBoundaryInputValidCreateCookbookCookbookDescriptioContain1Char() {
        String inputCookbookDes = "M";
        boolean expected = true;
        assertEquals(expected, vf.checkInputCookbookDescriptionCreateNewCookbook(inputCookbookDes));
    }

    @Test
    public void testBoundaryInputInvalidCreateCookbookCookbookDescriptioContain0Char() {
        String inputCookbookDes = "";
        boolean expected = false;
        assertEquals(expected, vf.checkInputCookbookDescriptionCreateNewCookbook(inputCookbookDes));
    }

    @Test
    public void testBoundaryInputInvalidCreateCookbookCookbookDescriptioContain201Char() {
        String inputCookbookDes = "Mùa World Cup đã đến và cùng đang quá trình chuẩn bị. " +
                "Sao nữa nhỉ? Tất nhiên sao gì nữa hòa cùng không khí đang nóng bừng này lên đi " +
                "Nhâm nhi vài món cùng gia đình và chiến hữu thưởng thức khung cầu ta";
        boolean expected = false;
        assertEquals(expected, vf.checkInputCookbookDescriptionCreateNewCookbook(inputCookbookDes));
    }

    @Test
    public void testBoundaryInputValidCreateCookbookCookbookDescriptioContain200Char() {
        String inputCookbookDes = "Mùa World Cup đã đến và cùng đang quá trình chuẩn bị. " +
                "Sao nữa nhỉ? Tất nhiên sao gì nữa hòa cùng không khí đang nóng bừng này lên đi " +
                "Nhâm nhi vài món cùng gia đình và chiến hữu thưởng thức khung cầu t";
        boolean expected = true;
        assertEquals(expected, vf.checkInputCookbookDescriptionCreateNewCookbook(inputCookbookDes));
    }

    //test duppicate name of cookbook
    @Test
    public void testEquivalentInputInvalidCookbookNameDupplicate2NameSame() {
        String inputCookbookName1 = "Món ăn ngày hè";
        String inputCookbookName2 = "Món ăn ngày hè";
        boolean expected = false;
        assertEquals(expected, vf.checkInputCookbookNameDupplicate(inputCookbookName1, inputCookbookName2));
    }

    @Test
    public void testEquivalentInputInvalidCookbookNameDupplicate2NameSameIgnorCase() {
        String inputCookbookName1 = "món ăn ngày Hè";
        String inputCookbookName2 = "Món ăn ngày hè";
        boolean expected = false;
        assertEquals(expected, vf.checkInputCookbookNameDupplicate(inputCookbookName1, inputCookbookName2));
    }

    @Test
    public void testEquivalentInputValidCookbookNameDupplicate2NameNotSameLowerCase() {
        String inputCookbookName1 = "món ăn ngày đông lạnh giá";
        String inputCookbookName2 = "món ăn ngày hè";
        boolean expected = true;
        assertEquals(expected, vf.checkInputCookbookNameDupplicate(inputCookbookName1, inputCookbookName2));
    }

    @Test
    public void testEquivalentInputValidCookbookNameDupplicate2NameNotSameSomeUpperCase() {
        String inputCookbookName1 = "Món ăn ngày đông lạnh giá";
        String inputCookbookName2 = "Món ăn ngày hè";
        boolean expected = true;
        assertEquals(expected, vf.checkInputCookbookNameDupplicate(inputCookbookName1, inputCookbookName2));
    }

    @Test
    public void testBoundaryInputInvalidCookbookNameDupplicate2NameSameLowerCase() {
        String inputCookbookName1 = "abc";
        String inputCookbookName2 = "abc";
        boolean expected = false;
        assertEquals(expected, vf.checkInputCookbookNameDupplicate(inputCookbookName1, inputCookbookName2));
    }

    @Test
    public void testBoundaryInputInvalidCookbookNameDupplicate2NameSameUpperCase() {
        String inputCookbookName1 = "ABC";
        String inputCookbookName2 = "ABC";
        boolean expected = false;
        assertEquals(expected, vf.checkInputCookbookNameDupplicate(inputCookbookName1, inputCookbookName2));
    }

    @Test
    public void testBoundaryInputInvalidCookbookNameDupplicate2NameSameSomeUpperCase() {
        String inputCookbookName1 = "AbC";
        String inputCookbookName2 = "AbC";
        boolean expected = false;
        assertEquals(expected, vf.checkInputCookbookNameDupplicate(inputCookbookName1, inputCookbookName2));
    }

    @Test
    public void testBoundaryInputValidCookbookNameDupplicate2NameNotSameSomeUpperCase() {
        String inputCookbookName1 = "AbCd";
        String inputCookbookName2 = "AbC";
        boolean expected = true;
        assertEquals(expected, vf.checkInputCookbookNameDupplicate(inputCookbookName1, inputCookbookName2));
    }

    @Test
    public void testBoundaryInputValidCookbookNameDupplicate2NameNotSameAllUpperCase() {
        String inputCookbookName1 = "ABCD";
        String inputCookbookName2 = "ABC";
        boolean expected = true;
        assertEquals(expected, vf.checkInputCookbookNameDupplicate(inputCookbookName1, inputCookbookName2));
    }

    @Test
    public void testBoundaryInputValidCookbookNameDupplicate2NameNotSameAllLowerCase() {
        String inputCookbookName1 = "abcd";
        String inputCookbookName2 = "abc";
        boolean expected = true;
        assertEquals(expected, vf.checkInputCookbookNameDupplicate(inputCookbookName1, inputCookbookName2));
    }

    //test input post step description
    @Test
    public void testEquivalenceInputValidPostStepDescription() {
        String inputStepDes = "Chuẩn bị 1 chén phô mai sợi 100 gram, 20 lá hoành thánh tươi";
        boolean expected = true;
        assertEquals(expected, vf.checkPostStepDescription(inputStepDes));
    }

    @Test
    public void testEquivalenceInputInvalidPostStepDescriptionMoreThanStandard() {
        String inputStepDes = "Chuẩn bị 1 chén phô mai sợi 100 gram, 20 lá hoành thánh tươi, " +
                "muỗng và một chén dầu ăn. Đến công đoạn gói hoành thánh, bạn đặt miếng hoành thánh lên thớt theo hình thoi " +
                "chiều chính diện, rồi cho vào nữa muỗng cà phê khoai nghiền và 1 muỗng cà phê phô mai sợi. Sau đó gấp miếng " +
                "hoành thánh lại thành hình tam giác, dùng dầu ăn để miết các cạnh cho bánh không bị bung. Tiếp tục gấp hai " +
                "cạnh bên lại ôm trọn phần nhân và xếp ra dĩa.";
        boolean expected = false;
        assertEquals(expected, vf.checkPostStepDescription(inputStepDes));
    }

    @Test
    public void testEquivalenceInputInvalidPostStepDescriptionLessThanStandard() {
        String inputStepDes = "";
        boolean expected = false;
        assertEquals(expected, vf.checkPostStepDescription(inputStepDes));
    }

    @Test
    public void testBoundaryInputValidPostStepDescriptioContain1Char() {
        String inputStepDes = "M";
        boolean expected = true;
        assertEquals(expected, vf.checkPostStepDescription(inputStepDes));
    }

    @Test
    public void testBoundaryInputInvalidPostStepDescriptioContain0Char() {
        String inputStepDes = "";
        boolean expected = false;
        assertEquals(expected, vf.checkPostStepDescription(inputStepDes));
    }

    @Test
    public void testBoundaryInputInvalidPostStepDescriptioContain201Char() {
        String inputStepDes = "Chuẩn bị 1 chén phô mai sợi 100 gram, 20 lá hoành thánh tươi, muỗng và một chén dầu ăn." +
                " Đến công đoạn gói hoành thánh, bạn đặt miếng hoành thánh lên " +
                "thớt theo hình thoi chiều chính diện, rồi cho vào nữ";
        boolean expected = false;
        assertEquals(expected, vf.checkPostStepDescription(inputStepDes));
    }

    @Test
    public void testBoundaryInputValidPostStepDescriptioContain200Char() {
        String inputStepDes = "Chuẩn bị 1 chén phô mai sợi 100 gram, 20 lá hoành thánh tươi, muỗng và một chén dầu ăn." +
                " Đến công đoạn gói hoành thánh, bạn đặt miếng hoành thánh lên " +
                "thớt theo hình thoi chiều chính diện, rồi cho vào n";
        boolean expected = true;
        assertEquals(expected, vf.checkPostStepDescription(inputStepDes));
    }

    //test input post step time
    @Test
    public void testEquivalenceInputValidPostStepTime() {
        String inputStepTime = "1 hour 15 minute";
        boolean expected = true;
        assertEquals(expected, vf.checkPostStepTime(inputStepTime));
    }

    @Test
    public void testEquivalenceInputInvalidPostStepTimeMoreThanStandard() {
        String inputStepTime = "10 hour 15 minute 20 second";
        boolean expected = false;
        assertEquals(expected, vf.checkPostStepTime(inputStepTime));
    }

    @Test
    public void testEquivalenceInputInvalidPostStepTimeLessThanStandard() {
        String inputStepTime = "";
        boolean expected = false;
        assertEquals(expected, vf.checkPostStepTime(inputStepTime));
    }

    @Test
    public void testBoundaryInputValidPostStepTimeContain1Char() {
        String inputStepTime = "1";
        boolean expected = true;
        assertEquals(expected, vf.checkPostStepTime(inputStepTime));
    }

    @Test
    public void testBoundaryInputInvalidPostStepTimeContain0Char() {
        String inputStepTime = "";
        boolean expected = false;
        assertEquals(expected, vf.checkPostStepTime(inputStepTime));
    }

    @Test
    public void testBoundaryInputInvalidPostStepTimeContain21Char() {
        String inputStepTime = "10 hour 15 minute 1sc";
        boolean expected = false;
        assertEquals(expected, vf.checkPostStepTime(inputStepTime));
    }

    @Test
    public void testBoundaryInputValidPostStepTimeContain20Char() {
        String inputStepTime = "10 hour 15 minute 1s";
        boolean expected = true;
        assertEquals(expected, vf.checkPostStepTime(inputStepTime));
    }

    //test input post step temperature
    @Test
    public void testEquivalenceInputValidPostStepTemperature() {
        String inputSteptemperature = "10°C";
        boolean expected = true;
        assertEquals(expected, vf.checkPostStepTemperature(inputSteptemperature));
    }

    @Test
    public void testEquivalenceInputInvalidPostStepTemperatureMoreThanStandard() {
        String inputSteptemperature = "1000000°C";
        boolean expected = false;
        assertEquals(expected, vf.checkPostStepTemperature(inputSteptemperature));
    }

    @Test
    public void testEquivalenceInputInvalidPostStepTemperatureLessThanStandard() {
        String inputSteptemperature = "1";
        boolean expected = false;
        assertEquals(expected, vf.checkPostStepTemperature(inputSteptemperature));
    }

    @Test
    public void testBoundaryInputValidPostStepTemperatureContain3Char() {
        String inputSteptemperature = "1°C";
        boolean expected = true;
        assertEquals(expected, vf.checkPostStepTemperature(inputSteptemperature));
    }

    @Test
    public void testBoundaryInputInvalidPostStepTemperatureContain2Char() {
        String inputSteptemperature = "1°";
        boolean expected = false;
        assertEquals(expected, vf.checkPostStepTemperature(inputSteptemperature));
    }

    @Test
    public void testBoundaryInputInvalidPostStepTemperatureContain7Char() {
        String inputSteptemperature = "10000°C";
        boolean expected = false;
        assertEquals(expected, vf.checkPostStepTemperature(inputSteptemperature));
    }

    @Test
    public void testBoundaryInputValidPostStepTemperatureContain6Char() {
        String inputSteptemperature = "1000°C";
        boolean expected = true;
        assertEquals(expected, vf.checkPostStepTemperature(inputSteptemperature));
    }
}