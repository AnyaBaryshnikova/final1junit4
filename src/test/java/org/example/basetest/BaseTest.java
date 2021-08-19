package org.example.basetest;


import org.example.managers.DriverManager;
import org.example.managers.InitManager;
import org.example.managers.PageManager;
import org.example.managers.TestPropManager;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import static org.example.utils.Constants.BASE_URL;

public class BaseTest {

    /**
     * Менеджер страничек
     */
    protected PageManager app = PageManager.getPageManager();

    /**
     * Менеджер WebDriver
     *
     */
    private final DriverManager driverManager = DriverManager.getDriverManager();

    @BeforeClass
    public static void beforeAll() {
        InitManager.initFramework();
    }

    @Before
    public void beforeEach() {
        driverManager.getDriver().get(TestPropManager.getTestPropManager().getProperty(BASE_URL));
    }
//
//    @AfterClass
//    public static void afterAll() {
//        InitManager.quitFramework();
//    }
}
