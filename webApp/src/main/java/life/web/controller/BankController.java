package life.web.controller;

import static com.google.common.base.Preconditions.checkNotNull;
import javax.inject.Inject;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bank/")
public class BankController {

  private final BankInterface bankInterface;

  @Inject
  public BankController(BankInterface bankInterface) {
    this.bankInterface = checkNotNull(bankInterface);
  }

  @RequestMapping("getAllTransactions")
  @ResponseBody
  public List<TableObject> getTableObjects() {
    return bankInterface.getTableObjects();
  }

  @RequestMapping(value = "monthlyExpensesList/{yearNumber}/{monthNumber}")
  public List<TableObject> getMonthlyExpensesList(@PathVariable int monthNumber, @PathVariable int yearNumber) {
    return bankInterface.getMonthlyExpensesList(monthNumber, yearNumber);
  }

  @RequestMapping(value = "monthlyIncomeList/{yearNumber}/{monthNumber}")
  public List<TableObject> getMonthlyIncomeList(@PathVariable int monthNumber, @PathVariable int yearNumber) {
    return bankInterface.getMonthlyIncomeList(monthNumber, yearNumber);
  }
}