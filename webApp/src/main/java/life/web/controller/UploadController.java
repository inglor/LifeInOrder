package life.web.controller;

import javax.inject.Inject;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import life.database.model.BankTransaction;
import life.file.parser.FileParser;

@RestController
@RequestMapping("/api/upload/")
public class UploadController {

  private FileParser fileParser;
  private BankingFacade bankingFacade;

  @Inject
  public UploadController(FileParser fileParser, BankingFacade bankingFacade) {
    this.fileParser = fileParser;
    this.bankingFacade = bankingFacade;
  }

  @RequestMapping(value = "transactions", method = RequestMethod.POST)
  @ResponseBody
  @SuppressWarnings(value = "unchecked")
  public boolean uploadTransactions(@RequestParam("file") MultipartFile multipartFile) {
    if (fileParser.canParse(multipartFile)) {
      bankingFacade.save((List<BankTransaction>) fileParser.parse(multipartFile));
      return true;
    }
    throw new UnsupportedOperationException("File type not support: " + multipartFile.getOriginalFilename());
  }
}
