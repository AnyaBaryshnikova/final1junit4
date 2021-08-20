//package org.example.tests;
//
//import io.qameta.allure.junit4.DisplayName;
//import org.example.basetest.BaseTest;
//import org.junit.Test;
//
//public class SecondTest extends BaseTest {
//    @Test
//    @DisplayName("Проверка второго сценария")
//    public void startTest() {
//        app.getMainPage()
//                .choseMenu("Вклады")
//                .choseCurrency("Доллары США")
//                .addDepositAmount("500 000")
//                .addMonths(12)
//                .addMounthlyPayment("20 000")
//                .selectCheckBox("Ежемесячная капитализация", true)
//                .checkAccuredInrest(920.60)
//                .checkRefill()
//                .checkAmountWithdrawn();
//
//    }
//}
