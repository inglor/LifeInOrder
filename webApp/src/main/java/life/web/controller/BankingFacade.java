package life.web.controller;

import static com.google.common.base.Preconditions.checkNotNull;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import life.database.model.BankTransaction;

@Named
class BankingFacade {

  private BankService bankService;

  @Inject
  public BankingFacade(BankService bankService) {
    this.bankService = checkNotNull(bankService);
  }

  void save(List<BankTransaction> bankTransactions) {
    bankService.saveBankTransactions(bankTransactions);
    for (BankTransaction bankTransaction : bankTransactions) {
      bankService.setMonthStat(bankTransaction);
    }
  }
}
