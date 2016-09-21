package life.database.dao;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import life.database.model.BankTransaction;

public interface BankTransactionDao extends CrudRepository<BankTransaction, Long> {

  List<BankTransaction> findAllByOrderByTransactiondateDesc();

  List<BankTransaction> findByTransactiondateAndDescriptionAndCost(LocalDate localDate, String description,
                                                                   Double cost);

}
