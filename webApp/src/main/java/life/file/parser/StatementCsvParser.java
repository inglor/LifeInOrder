package life.file.parser;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import life.database.model.BankTransaction;
import life.file.DateUtilsImpl;

@Component
@Transactional
public class StatementCsvParser implements FileParser<MultipartFile, List<BankTransaction>> {
  private static final Logger LOG = LoggerFactory.getLogger(StatementCsvParser.class);
  private DateUtilsImpl dateUtils = new DateUtilsImpl();

  /**
   * This will read the file and check if the headers are 3 or 5 columns which correspond to
   * statement csv file or midata csv file. Finally file should have a .csv extension.
   *
   * @param multipartFile file to check
   * @return true if {@link StatementCsvParser} can parse it
   */
  @Override
  public boolean canParse(MultipartFile multipartFile) {
    try (CSVReader reader = new CSVReader(new InputStreamReader(multipartFile.getInputStream()),
        CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, 1)) {
      String[] headers = reader.readNext();
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
   * date : DD/MM/YYYY
   * description [a-zA-Z0-9]
   * amount ##.#
   * <p>
   * Midata CSV file expected to have 5 headers
   * date : DD/MM/YYYY
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
    try (CSVReader reader = new CSVReader(new InputStreamReader(multipartFile.getInputStream()),
        CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, 1)) {
      List<String[]> lines = reader.readAll();
      if (lines.get(0).length == 5) {
        return lines.stream()
            .filter(l -> l.length == 5)
            .map(line -> new BankTransaction(dateUtils.convertTextToDate(line[0]), line[2], getValue(line[3])))
            .collect(Collectors.toList());
      } else if (lines.get(0).length == 3) {
        return lines.stream()
            .filter(l -> l.length == 3)
            .map(line -> new BankTransaction(dateUtils.convertTextToDate(line[0]), line[1], getValue(line[2])))
            .collect(Collectors.toList());
      }
    } catch (IOException e) {
      LOG.error("Unable to read file {}", multipartFile.getOriginalFilename(), e);
    }
    return Collections.emptyList();
  }

  private Double getValue(String value) {
    return Double.parseDouble(value.replace("£", ""));
  }
}
