package life.database.converter;

import javax.annotation.Nullable;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.YearMonth;

@Converter(autoApply = true)
public class YearMonthAttributeConverter implements AttributeConverter<YearMonth, Date> {

  @Override
  public Date convertToDatabaseColumn(@Nullable YearMonth yearMonth) {
    return (yearMonth == null ? null : Date.valueOf(yearMonth.atDay(1)));
  }

  @Override
  public YearMonth convertToEntityAttribute(@Nullable Date sqlDate) {
    return (sqlDate == null ? null : YearMonth.from(sqlDate.toLocalDate()));
  }
}
