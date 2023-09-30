package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amountInput = $("[data-test-id='amount'] input");  // сумма
    private final SelenideElement fromInput = $("[data-test-id=from] input");  // с какой карты
    private SelenideElement transferButton = $("[data-test-id='action-transfer']"); // кнопка перевода
    private final SelenideElement transferHead = $(byText("Пополнение карты")); // заголовок
    private final SelenideElement errorMessage = $("[data-test-id='error-notification']");  // ошибка
    public TransferPage(){
        transferHead.shouldBe(visible);
    }
    public DashboardPage makeValidTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo){
        makeTransfer(amountToTransfer, cardInfo);
        return new DashboardPage();
    }
    // ниже метод, который заполняет поля карты
    public void makeTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo){
        amountInput.setValue(amountToTransfer);
        fromInput.setValue(cardInfo.getCardNumber());
        transferButton.click();
    }
    public void findErrorMessage(String expectedText){    // находит сообщение об ошибке
        errorMessage.shouldHave(exactText(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }
}
