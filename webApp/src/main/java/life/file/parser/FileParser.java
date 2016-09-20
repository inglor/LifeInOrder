package life.file.parser;

public interface FileParser<I, O> {

  boolean canParse(I i);

  O parse(I i);
}
