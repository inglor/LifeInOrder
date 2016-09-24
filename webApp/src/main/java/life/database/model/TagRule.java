package life.database.model;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import com.google.common.collect.ComparisonChain;

@Entity
@Table(name = "TAG_RULE")
public class TagRule implements Serializable, Comparable<TagRule> {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "DESCRIPTION", nullable = false)
  private String description;

  @Column(name = "TAGS", nullable = false)
  @ElementCollection
  private List<String> tags;

  public TagRule() {
  }

  public TagRule(String description, List<String> tags) {
    this.description = description;
    this.tags = tags;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TagRule tagRule = (TagRule) o;
    return Objects.equals(description, tagRule.description)
        && Objects.equals(tags, tagRule.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, tags);
  }

  @Override
  public int compareTo(TagRule that) {
    Comparator<List<String>> tagComparator = Comparator.comparing(new Function<List<String>, String>() {
      private String res = "";

      @Override
      public String apply(List<String> tags) {
        for (String tag : tags) {
          res += tag;
        }
        return res;
      }
    });
    return ComparisonChain.start()
        .compare(this.description, that.description)
        .compare(this.getTags(), that.getTags(), tagComparator)
        .result();
  }
}
