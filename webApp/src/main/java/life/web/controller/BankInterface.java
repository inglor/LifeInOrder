package life.web.controller;

import java.util.List;
import life.database.model.BankTransaction;

interface BankInterface {
  List<TableObject> getTableObjects();

  List<TableObject> getMonthlyExpensesList(int monthNumber, int yearNumber);

  List<TableObject> getMonthlyIncomeList(int monthNumber, int yearNumber);

  void saveBankTransactions(List<BankTransaction> bankTransactions);
}
