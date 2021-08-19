package org.example.pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class DepositPage extends BasePage {

    @FindBy(xpath = "//h1")
    WebElement title;

    //Валюта
    @FindBy(xpath = "//div[@class='calculator__currency-content']")
    WebElement currency;

    //Сумма вклада
    @FindBy(xpath = "//input[@name='amount']")
    WebElement depositAmount;
    //Есть сейчас
    @FindBy(xpath = "//span[@class='js-calc-amount']")
    WebElement amountNow;

    // срок вклада
    @FindBy(xpath = "//li[contains(text(), 'месяц')]")
    List<WebElement> monthsList;

    // Ежемесячный платеж
    @FindBy(xpath = "//input[@name='replenish']")
    WebElement monthlyPayment;

    //Пополнение за все месяцы
    @FindBy(xpath = "//span[@class='js-calc-replenish']")
    WebElement replenishment;

    // капитализация
    @FindBy(xpath = "//span[contains(text(), 'капитализация')]/../..//div[contains(@class, 'calculator__check')]")
    WebElement capitalization;

    //частичное снятие
    @FindBy(xpath = "//span[contains(text(), 'Частичное снятие')]/../..//div[contains(@class, 'calculator__check')]")
    WebElement partialWithdraw;

    // Начисленный %
    @FindBy(xpath = "//span[@class='js-calc-earned']")
    WebElement accuredInrest;


    //сумма к снятию
    @FindBy(xpath = "//span[@class='js-calc-result']")
    WebElement amountWithdarwn;

    // выбранное количество месяцев
    @FindBy(xpath = "//div[@class='jq-selectbox__select-text']")
    WebElement months;




    public DepositPage checkDepositPageOpen() {
        waitUtilElementToBeVisible(title);
        Assert.assertTrue("Мы не на странице со вкладами", title.getText().contains("Вклады"));
        return this;

    }

    /**
     * Выбираем валюту
     *
     * @param name название валюты
     * @return возвращаем ту же страничку
     */
    @Step("Выбираем валюту {name} на странице со вкладами")
    public DepositPage choseCurrency(String name) {
        scrollWithOffset(currency, 0, -500);
        if (name.equals("Рубли")) {
            WebElement rubCurr = currency.findElement(By.xpath("./label[1]/input"));
            rubCurr.click();
            return this;
        }
        if (name.equals("Доллары США")) {
            WebElement dolCurr = currency.findElement(By.xpath("./label[2]/input"));
            dolCurr.click();
            return this;
        }
        Assert.fail("Валюта '" + name + "' не найдена!");
        return this;
    }

    /**
     * Вводим сумму вклада
     *
     * @param money сумма
     * @return возвращаем ту же страничку
     */
    @Step("Вводим сумму вклада {money}")
    public DepositPage addDepositAmount(int money) {

        fillFields(depositAmount, money + "");
        wait.until(ExpectedConditions.textToBePresentInElement(amountNow, money + ""));
        return this;
    }

    /**
     * Добавляем срок депозита
     * @param amount количество месяцев, срок
     * @return
     */
    @Step()
    public DepositPage addMonths(int amount){
        for (WebElement month : monthsList) {
            if (month.getText().contains(amount + "")) {
                waitUtilElementToBeClickable(month).click();
                return this;
            }
        }
        Assert.fail("Невозможно указать срок депозита равный " + amount + "мес.");
        return this;
    }

    /**
     * Вводим сумму ежемесячного пополнения
     *
     * @param money сумма
     * @return возвращаем ту же страничку
     */
    @Step("Вводим сумму ежемесячного платежа {money}")
    public DepositPage addMounthlyPayment(int money) {

        fillFields(monthlyPayment, money + "");
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(replenishment, "0")));
        return this;
    }

    /**
     * Поставить или убрать галочку
     * @param strElement название элемента
     * @param flag значение
     * @return
     */
    @Step("Ставим галочку на поле {strElement}")
    public DepositPage selectCheckBox(String strElement, boolean flag){
        switch (strElement){
            case "Ежемесячная капитализация":
                checkCheckBox(capitalization, flag);
                break;
            case "Частичное снятие":
                checkCheckBox(partialWithdraw, flag);
                break;
            default:
                Assert.fail("Поле с наименованием '" + strElement + "' отсутствует на странице");
        }
        return this;
    }


    /**
     * Проверяем начисленный процент
     * @param expected процент, который ожидаем увидеть
     * @return
     */
    public DepositPage checkAccuredInrest(double expected){
        double actual = Double.parseDouble(accuredInrest.getText().replace(",", ".").replace(" ", ""));
        Assert.assertTrue("Начисленный процент, который ожидали увидеть " + expected + ""
                + "не совпадает с фактическим: " + actual, actual == expected);

        return this;

    }

    /**
     * Проверяем пополнение за все месяцы
     * @return
     */
    public DepositPage checkRefill(){

        int mpayment = Integer.parseInt(accuredInrest.getText().replace(" ", ""));
        int monthsAmount = Integer.parseInt(months.getText().replaceAll("[^\\d.]", ""));

        int actual = Integer.parseInt(replenishment.getText().replace(" ", ""));

        Assert.assertTrue("Пополнение за все месяцы не совпадает с ожидаемым", mpayment * (monthsAmount - 1) == actual);

        return this;

    }

    /**
     * Проверяем сумму к снятию
     * @return
     */
    public DepositPage checkAmountWithdrawn(){

        //поплнение за все месяцы
        int repl = Integer.parseInt(replenishment.getText().replace(" ", ""));
        //начисленный процент
        double proc = Double.parseDouble(accuredInrest.getText().replace(",", ".").replace(" ", ""));
        //сумма вклада
        int summVklada = Integer.parseInt(depositAmount.getText().replace(" ", ""));

        double expected = repl + proc + summVklada;

        double actual = Double.parseDouble(amountWithdarwn.getText().replace(",", ".").replace(" ", ""));


        Assert.assertTrue("Сумма к снятию в конце не совпадает с ожидаемой", actual == expected);
        return this;

    }

}
