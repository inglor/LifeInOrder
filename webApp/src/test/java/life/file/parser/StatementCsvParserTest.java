package life.file.parser;

import static org.testng.Assert.assertEquals;
import java.time.LocalDate;
import java.util.List;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.google.common.collect.Lists;
import life.database.model.BankTransaction;

public class StatementCsvParserTest {
  private StatementCsvParser statementCsvParser;

  @DataProvider(name = "canParseData")
  public static Object[][] canParseData() {
    return new Object[][]{
        {"validStatement1.csv", "8/3/16,D3CFBZ2PVYYMX15Z6B,-100.16\n8/22/16,YIQLCSKE4S1391BKJM,1617.53\n", true},
        {"validStatement2.csv", "8/3/16,D3CFBZ2PVYYMX15Z6B,-100.16\n\r8/22/16,YIQLCSKE4S1391BKJM,1617.53\n\r", true},
        {"validStatement3.csv", "8/3/16,D3CFBZ2PVYYMX15Z6B,-100.16\n", true},
        {"validStatement4.csv", "8/3/16,D3CFBZ2PVYYMX15Z6B,-100.16\n\r", true},
        {"validStatement5.csv", "8/3/16,D3CFBZ2PVYYMX15Z6B,-100.16\r", true},
        {"validStatement6.csv", "8/3/16,D3CFBZ2PVYYMX15Z6B,-100.16", true},
        {"validMidata1.csv", "03/08/2016,xxx,xxx,-100.16,-£30\n03/08/2016,xxx,xxx,-100.16,-£30\n", true},
        {"validMidata2.csv", "03/08/2016,xxx,xxx,-100.16,-£30\n\r03/08/2016,xxx,xxx,-100.16,-£30\n\r", true},
        {"validMidata3.csv", "03/08/2016,xxx,xxx,-100.16,-£30\n", true},
        {"validMidata4.csv", "03/08/2016,xxx,xxx,-100.16,-£30\n\r", true},
        {"validMidata5.csv", "03/08/2016,xxx,xxx,-100.16,-£30\r", true},
        {"validMidata6.csv", "03/08/2016,xxx,xxx,-100.16,-£30", true},
        {"invalidColumns1.csv", "0,1", false},
        {"invalidColumns2.csv", "0,1,2,3", false},
        {"invalidColumns3.csv", "0,1,2,3,4,5", false},
        {"invalidColumns4.csv", "0,1,2,3,4,5,6,7", false},
        {"invalidExtension.tsv", "0,1,2", false}
    };
  }

  @DataProvider(name = "parseData")
  public static Object[][] parseData() {
    return new Object[][]{
        {"validStatement1.csv", "8/3/16,D3CFBZ2PVYYMX15Z6B,-100.16\n8/22/16,YIQLCSKE4S1391BKJM,1617.53\n",
            Lists.newArrayList(new BankTransaction(LocalDate.of(2016, 8, 3), "D3CFBZ2PVYYMX15Z6B", -100.16d),
                new BankTransaction(LocalDate.of(2016, 8, 22), "YIQLCSKE4S1391BKJM", 1617.53d))},
        {"validMidata1.csv", "03/08/2016,xxx,xxx,-£100.16,-£30\n12/08/2016,yyy,yyy,-£200.16,-£50\n",
            Lists.newArrayList(new BankTransaction(LocalDate.of(2016, 8, 3), "xxx", -100.16d),
                new BankTransaction(LocalDate.of(2016, 8, 12), "yyy", -200.16d))}
    };
  }

  @BeforeClass
  public void setUp() throws Exception {
    this.statementCsvParser = new StatementCsvParser();
  }

  @Test(dataProvider = "canParseData")
  public void canParse(String name, String contents, boolean expectedResult) throws Exception {
    assertEquals(statementCsvParser.canParse(createMultipartFile(name, contents)), expectedResult, name);
  }

  @Test(dataProvider = "parseData")
  public void parse(String name, String contents, List<BankTransaction> expectedResults) throws Exception {
    List<BankTransaction> list = statementCsvParser.parse(createMultipartFile(name, contents));
    assertEquals(list, expectedResults);

  }

  private MultipartFile createMultipartFile(String name, String content) {
    return new MockMultipartFile(name, name, null, content.getBytes());
  }
}