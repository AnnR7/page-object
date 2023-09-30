package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

  public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = ", баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
      heading.shouldBe(visible);
    }
    public int getCardBalance(DataHelper.CardInfo cardInfo) {
      // TODO: перебрать все карты и найти по атрибуту data-test-id
      var text = cards.findBy(text((cardInfo.getCardNumber().substring(15)))).getText();
      return extractBalance(text);
    }
    private int extractBalance(String text) {
      var start = text.indexOf(balanceStart);
      var finish = text.indexOf(balanceFinish);
      var value = text.substring(start + balanceStart.length(), finish);
      return Integer.parseInt(value);
    }
                 // ниже метод, который умеет выбирать карту для перевода на странице дашборда
    public TransferPage selectCardToTransfer(DataHelper.CardInfo cardInfo ) {
      cards.findBy(attribute("data-test-id", cardInfo.getTestId())).$("button").click();
      return new TransferPage();
    }
}

