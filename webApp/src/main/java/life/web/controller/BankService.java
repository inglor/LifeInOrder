package life.web.controller;

import javax.inject.Named;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import life.database.dao.BankTransactionDao;
import life.database.dao.MonthStatDao;
import life.database.model.BankTransaction;
import life.database.model.MonthStat;
import life.util.BankTransactionUtil;

@Named
public class BankService implements BankInterface {

  @Value("${transactions.directory}")
  public String inputDirectory;

  private MonthStatDao monthStatDao;
  private BankTransactionDao bankTransactionDao;
  private BankTransactionUtil bankTransactionUtil;

  public BankService() {
  }

  @Autowired
  public BankService(MonthStatDao monthStatDao, BankTransactionDao bankTransactionDao) {
    this.monthStatDao = monthStatDao;
    this.bankTransactionDao = bankTransactionDao;
    bankTransactionUtil = new BankTransactionUtil();
  }

  @Override
  public List<TableObject> getTableObjects() {
    List<BankTransaction> allBankTransactions = bankTransactionDao.findAllByOrderByTransactiondateDesc();
    return bankTransactionUtil.getTableObjectList(allBankTransactions);
  }

  @Override
  public List<TableObject> getMonthlyExpensesList(int monthNumber, int yearNumber) {
    List<BankTransaction> bankTransactionList = new ArrayList<BankTransaction>();
    for (BankTransaction bankTransaction : bankTransactionDao.findAllByOrderByTransactiondateDesc()) {
      if ((bankTransaction.getTransactiondate().getMonthValue() == monthNumber)
          && (bankTransaction.getTransactiondate().getYear() == yearNumber)
          && (bankTransaction.getCost() < 0)) {
        bankTransactionList.add(bankTransaction);
      }
    }
    return bankTransactionUtil.getTableObjectList(bankTransactionList);
  }

  @Override
  public List<TableObject> getMonthlyIncomeList(int monthNumber, int yearNumber) {
    List<BankTransaction> bankTransactionList = new ArrayList<BankTransaction>();
    for (BankTransaction bankTransaction : bankTransactionDao.findAllByOrderByTransactiondateDesc()) {
      if ((bankTransaction.getTransactiondate().getMonthValue() == monthNumber)
          && (bankTransaction.getTransactiondate().getYear() == yearNumber)
          && (bankTransaction.getCost() > 0)) {
        bankTransactionList.add(bankTransaction);
      }
    }
    return bankTransactionUtil.getTableObjectList(bankTransactionList);
  }

  @Override
  public void saveBankTransactions(List<BankTransaction> bankTransactions) {
    for (BankTransaction bankTransaction : bankTransactions) {
      List<BankTransaction> transactions = bankTransactionDao.findByTransactiondateAndDescriptionAndCost(
          bankTransaction.getTransactiondate(),
          bankTransaction.getDescription(),
          bankTransaction.getCost());
      if (transactions.size() == 0) {
        bankTransactionDao.save(bankTransaction);
      }
    }
  }

  public void setMonthStat(BankTransaction bankTransaction) {
    DecimalFormat decimalFormat = new DecimalFormat("#.00");
    double income = 0;
    double expense = 0;
    double profit;

    LocalDate transactionDate = bankTransaction.getTransactiondate();
    YearMonth yearMonth = YearMonth.of(transactionDate.getYear(), transactionDate.getMonthValue());
    if (bankTransaction.getCost() > 0) {
      income = bankTransaction.getCost();
    } else {
      expense = bankTransaction.getCost();
    }
    profit = income + expense;

    if (monthStatDao.findAllByOrderByYearMonthDesc().isEmpty()) {
      monthStatDao.save(new MonthStat(yearMonth, income, expense, profit));
    } else {
      MonthStat monthStatForUpdate = monthStatDao.findByYearMonth(yearMonth);
      if (monthStatForUpdate == null) {
        monthStatDao.save(new MonthStat(yearMonth, income, expense, profit));
      } else {
        double calculatedIncome = Double.parseDouble(decimalFormat.format(income + monthStatForUpdate.getIncome()));
        double calculatedExpenses = Double.parseDouble(decimalFormat.format(expense + monthStatForUpdate.getExpense()));
        double calculatedProfits = Double.parseDouble(decimalFormat.format(profit + monthStatForUpdate.getProfit()));
        monthStatForUpdate.setIncome(calculatedIncome);
        monthStatForUpdate.setExpense(calculatedExpenses);
        monthStatForUpdate.setProfit(calculatedProfits);
        monthStatDao.save(monthStatForUpdate);
      }
    }
  }
}
