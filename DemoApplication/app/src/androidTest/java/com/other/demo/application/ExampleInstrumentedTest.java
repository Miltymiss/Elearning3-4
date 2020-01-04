package com.other.demo.application;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.Test;


public class ExampleInstrumentedTest extends UiAutomatorTestCase {

    @Test
    public void testDemo() throws UiObjectNotFoundException {

        UiDevice instance = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject Calculator = new UiObject(new UiSelector().description("Demo"));

        Calculator.clickAndWaitForNewWindow();
        UiObject seven = new UiObject(new UiSelector().resourceId("com.other.demo.application:id/username"));
        seven.setText("111");


        UiObject seven1 = new UiObject(new UiSelector().resourceId("com.other.demo.application:id/password"));
        seven1.setText("111");


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        UiObject login = new UiObject(new UiSelector().resourceId("com.other.demo.application:id/login"));
        login.click();

        UiObject cardView = new UiObject(new UiSelector().resourceId("com.other.demo.application:id/cardView"));
        cardView.click();

        UiObject cardView1 = new UiObject(new UiSelector().resourceId("com.other.demo.application:id/cardView1"));
        cardView1.clickAndWaitForNewWindow();

        instance.pressHome();
    }

}
