package life.file.parser;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import life.database.model.BankTransaction;
import life.file.DateUtilsImpl;

@Component
@Transactional
public class StatementCsvParser implements FileParser<MultipartFile, List<BankTransaction>> {
  private static final Logger LOG = LoggerFactory.getLogger(StatementCsvParser.class);
  private static final String STATEMENT_CSV_REGEX = "\\d{1,2}/\\d{1,2}/\\d{2},[a-zA-Z0-9].*,[+|-]?\\d+(\\.\\d+)?";
  private static final String MIDATA_CSV_REGEX =
      "\\d{2}/\\d{2}/\\d{4},.{2,3},(?<=,)[^,]+(?=,),[+|-]£\\d+(\\.\\d+)?,[+|-]£\\d+(\\.\\d+)?";
  private static final Predicate<String[]> STATEMENT_FILTER = f ->
      Pattern.compile(STATEMENT_CSV_REGEX).matcher(String.join(",", f)).find();
  private static final Predicate<String[]> MIDATA_FILTER = f ->
      Pattern.compile(MIDATA_CSV_REGEX).matcher(String.join(",", f)).find();
  private final DateUtilsImpl dateUtils = new DateUtilsImpl();
  private final CsvParser parser;

  StatementCsvParser() {
    CsvParserSettings settings = new CsvParserSettings();
    settings.getFormat().setLineSeparator("\n");
    parser = new CsvParser(settings);
  }

  /**
   * This will read the file and check if the headers are 3 or 5 columns which correspond to
   * statement csv file or midata csv file. Finally file should have a .csv extension.
   *
   * @param multipartFile file to check
   * @return true if {@link StatementCsvParser} can parse it
   */
  @Override
  public boolean canParse(MultipartFile multipartFile) {
    try {
      parser.beginParsing(multipartFile.getInputStream());
      String[] headers = parser.parseNext();
      if (headers.length != 3 && headers.length != 5) {
        return false;
      }
    } catch (IOException e) {
      LOG.error("Unable to read file {}", multipartFile.getOriginalFilename(), e);
    }
    return multipartFile.getOriginalFilename().toLowerCase().endsWith(".csv");
  }

  /**
   * Statement CSV file expected to have 3 headers
   * date : MM/dd/yyyy
   * description [a-zA-Z0-9]
   * amount ##.#
   * <p>
   * Midata CSV file expected to have 5 headers
   * date : dd/MM/yyyy
   * type : [a-zA-Z0-9]{3}
   * description [a-zA-Z0-9]
   * amount £##.#
   * balance : £##.#
   *
   * @param multipartFile csv file to parse
   * @return list of BankTransaction
   */
  @Override
  public List<BankTransaction> parse(MultipartFile multipartFile) {
    try {
      List<String[]> lines = parser.parseAll(multipartFile.getInputStream());
      return Stream.concat(
          lines.stream()
              .filter(MIDATA_FILTER)
              .map(line -> new BankTransaction(dateUtils.convertTextToDate(line[0], "dd/MM/yyyy"),
                  line[2], Double.parseDouble(line[3].replace("£", "")))),
          lines.stream()
              .filter(STATEMENT_FILTER)
              .map(line -> new BankTransaction(dateUtils.convertTextToDate(line[0]),
                  line[1], Double.parseDouble(line[2].replace("£", ""))))
      ).sorted().collect(Collectors.toList());
    } catch (IOException e) {
      LOG.error("Unable to read file {}", multipartFile.getOriginalFilename(), e);
    }
    return Collections.emptyList();
  }
}
