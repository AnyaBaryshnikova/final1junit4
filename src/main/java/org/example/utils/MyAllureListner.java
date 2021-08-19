package org.example.utils;


import io.qameta.allure.Attachment;
import io.qameta.allure.junit4.AllureJunit4;
import org.example.managers.DriverManager;
import org.junit.runner.notification.Failure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class MyAllureListner extends AllureJunit4 {

    @Override
    public void testFailure(final Failure failure) {
        addScreen();
        super.testFailure(failure);
    }

    @Attachment(value = "screenshot", type = "image/png")
    public static byte[] addScreen(){
        return ((TakesScreenshot) DriverManager.getDriverManager().getDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
