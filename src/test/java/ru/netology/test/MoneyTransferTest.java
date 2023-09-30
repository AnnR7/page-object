package ru.netology.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.*;

class MoneyTransferTest {
  DashboardPage dashboardPage;
  CardInfo firstCardInfo;
  CardInfo secondCardInfo;

  @BeforeEach
  void setup(){
    var loginPage = open("http://localhost:9999", LoginPage.class);
    var authInfo = getAuthInfo();
    var verificationPage = loginPage.validLogin(authInfo);
    var verificationCode = getVerificationCode(authInfo);
    dashboardPage = verificationPage.validVerify(verificationCode);
    firstCardInfo = getFirstCardInfo();
    secondCardInfo = getSecondCardInfo();
  }
    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {
      var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
      var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
      var amount = generateValidAmount(firstCardBalance);
      var expectedBalanceFirstCard = firstCardBalance - amount;
      var expectedBalanceSecondCard = secondCardBalance + amount;
      var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
      dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo);
      var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
      var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
      Assertions.assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
      Assertions.assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }
  @Test
  void shouldGetErrorMessageIfAmountMoreBalance() {
    var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
    var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
    var amount = generateInvalidAmount(secondCardBalance);
    var transferPage = dashboardPage.selectCardToTransfer(firstCardInfo);
    transferPage.makeValidTransfer(String.valueOf(amount), secondCardInfo);
    transferPage.findErrorMessage("Ошибка!");
    var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
    var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
    Assertions.assertEquals(firstCardBalance, actualBalanceFirstCard);
    Assertions.assertEquals(secondCardBalance, actualBalanceSecondCard);
  }
}

