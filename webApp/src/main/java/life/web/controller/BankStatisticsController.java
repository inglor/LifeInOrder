package life.web.controller;

import static com.google.common.base.Preconditions.checkNotNull;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import life.database.model.MonthStat;

@RestController
@RequestMapping("/api/statistics/")
public class BankStatisticsController {

  private final BankStatisticsInterface bankStatisticsInterface;

  @Autowired
  public BankStatisticsController(BankStatisticsInterface bankStatisticsInterface) {
    this.bankStatisticsInterface = checkNotNull(bankStatisticsInterface);
  }

  @RequestMapping(value = "totalIncome")
  public double getTotalIncome() {
    return bankStatisticsInterface.getTotalIncome();
  }

  @RequestMapping(value = "totalExpenses")
  public double getTotalExpenses() {
    return bankStatisticsInterface.getTotalExpenses();
  }

  @RequestMapping("monthly")
  public List<MonthStat> getMonthlyStatistics() {
    return bankStatisticsInterface.getMonthlyStatistics();
  }

  @RequestMapping(value = "monthlyIncome/{yearNumber}/{monthNumber}")
  public double getMonthlyIncome(@PathVariable int monthNumber, @PathVariable int yearNumber) {
    return bankStatisticsInterface.getMonthlyIncome(monthNumber, yearNumber);
  }

  @RequestMapping(value = "monthlyExpenses/{yearNumber}/{monthNumber}")
  public double getMonthlyExpenses(@PathVariable int monthNumber, @PathVariable int yearNumber) {
    return bankStatisticsInterface.getMonthlyExpenses(monthNumber, yearNumber);
  }

  @RequestMapping(value = "yearlyExpenses/{yearNumber}")
  public double getYearlyExpenses(@PathVariable int yearNumber) {
    return bankStatisticsInterface.getYearlyExpenses(yearNumber);
  }

  @RequestMapping(value = "yearlyIncome/{yearNumber}")
  public double getYearlyIncome(@PathVariable int yearNumber) {
    return bankStatisticsInterface.getYearlyIncome(yearNumber);
  }

  @RequestMapping(value = "initialBalance")
  public double getInitialBalance() {
    return bankStatisticsInterface.getInitialBalance();
  }

  @RequestMapping(value = "medianMonthlyExpense")
  public double getMedianMonthlyExpense() {
    return bankStatisticsInterface.getMedianMonthlyExpense();
  }

  @RequestMapping(value = "medianMonthlyIncome")
  public double getMedianMonthlyIncome() {
    return bankStatisticsInterface.getMedianMonthlyIncome();
  }

}
