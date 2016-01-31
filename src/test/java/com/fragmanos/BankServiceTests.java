package com.fragmanos;

import com.fragmanos.database.dao.BankTransactionDao;
import com.fragmanos.database.dao.MonthStatDao;
import com.fragmanos.database.model.BankTransaction;
import com.fragmanos.web.controller.BankService;
import org.joda.time.LocalDate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class BankServiceTests {

  private BankService bankService;
  private MonthStatDao monthStatDaoMock;
  private BankTransactionDao bankTransactionDaoMock;
  private int month;
  private int year;
  private List<BankTransaction> bankTransactionList;

  @BeforeMethod
  public void setUp() throws Exception {
    monthStatDaoMock = mock(MonthStatDao.class);
    bankTransactionDaoMock = mock(BankTransactionDao.class);
    bankService = new BankService(monthStatDaoMock,bankTransactionDaoMock);
    month=10;
    year=2015;
    bankTransactionList = new ArrayList<BankTransaction>();
    when(bankTransactionDaoMock.findAllByOrderByTransactiondateDesc()).thenReturn(bankTransactionList);
  }

  @Test
  public void testObjectReturnedMatchesExpected() throws Exception {
    bankTransactionList.add(new BankTransaction(new LocalDate(2015, 10, 1), "TEST1", -1000.0));
    assertEquals(1, bankService.getMonthlyExpensesList(month, year).size());
  }

  @Test
  public void testMonthlyExpensesListMatchesExpected() throws Exception {
    bankTransactionList.add(new BankTransaction(new LocalDate(2015, 10, 1), "TEST1", -1000.0));
    assertEquals(1, bankService.getMonthlyExpensesList(month, year).size());
  }

  @Test
  public void testMonthlyExpensesListWithPositiveCostMatchesExpected() throws Exception {
    bankTransactionList.add(new BankTransaction(new LocalDate(2015, 10, 1), "TEST1", 1000.0));
    assertEquals(0, bankService.getMonthlyExpensesList(month, year).size());
  }

  @Test
  public void testMonthlyIncomeListMatchesExpected() throws Exception {
    bankTransactionList.add(new BankTransaction(new LocalDate(2015, 10, 1), "TEST1", 1000.0));
    assertEquals(1, bankService.getMonthlyIncomeList(month, year).size());
  }

  @Test
  public void testMonthlyIncomeListWithNegativeCostMatchesExpected() throws Exception {
    bankTransactionList.add(new BankTransaction(new LocalDate(2015, 10, 1), "TEST1", -1000.0));
    assertEquals(0, bankService.getMonthlyIncomeList(month,year).size());
  }


}