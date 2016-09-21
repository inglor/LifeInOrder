package life.database.dao;

import java.time.YearMonth;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import life.database.model.MonthStat;

public interface MonthStatDao extends CrudRepository<MonthStat, Long> {

  List<MonthStat> findAllByOrderByYearMonthDesc();

  MonthStat findByYearMonth(YearMonth yearMonth);
}
