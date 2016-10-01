package life.util;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections.ListUtils;
import life.database.model.BankTransaction;
import life.web.controller.TableObject;

@Named
public class BankTransactionUtil {

  public List<BankTransaction> unionOfBankTransactions(List<BankTransaction> totalBankTransactionList,
                                                       List<BankTransaction> fileBankTransactionList) {
    Set<BankTransaction> set = new HashSet<>();
    set.addAll(totalBankTransactionList);
    set.addAll(fileBankTransactionList);
    return new ArrayList<>(set);
  }

  @SuppressWarnings("unchecked")
  public List<BankTransaction> differenceOfBankTransactions(List<BankTransaction> listA, List<BankTransaction> listB) {
    return (List<BankTransaction>) ListUtils.removeAll(listA, listB);
  }

  public List<TableObject> getTableObjectList(List<BankTransaction> bankTransactionList) {
    return bankTransactionList.stream().map(b -> TableObject.newBuilder()
        .description(b.getDescription())
        .cost(b.getCost())
        .date(b.getTransactiondate().toString())
        .tags(b.getTags())
        .build()).collect(Collectors.toList());
  }
}
