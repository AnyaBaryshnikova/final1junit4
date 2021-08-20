package org.example.tests;

import io.qameta.allure.junit4.DisplayName;
import org.example.basetest.BaseTest;
import org.junit.Test;

public class FirstTest extends BaseTest {

    @Test
    @DisplayName("Проверка первого сценария")
    public void startTest() {
        app.getMainPage()
                .choseMenu("Вклады")
                .choseCurrency("Рубли")
                .addDepositAmount("300 000")
                .addMonths(6)
                .addMounthlyPayment("50 000")
                .selectCheckBox("Ежемесячная капитализация", true)
                .checkAccuredInrest(12243.26)
                .checkRefill()
                .checkAmountWithdrawn();

    }
}
