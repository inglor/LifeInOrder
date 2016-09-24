package life.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.beust.jcommander.internal.Lists;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ComparisonChain;

@Entity
@Table(name = "BANKTRANSACTION")
public class BankTransaction implements Serializable, Comparable<BankTransaction> {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "TRANSACTIONDATE", nullable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate transactiondate;

  @Column(name = "DESCRIPTION", nullable = false)
  private String description;

  @Column(name = "COST", nullable = false)
  private Double cost;

//  @Column(name = "TAGS", nullable = false)
//  @ElementCollection
//  private List<String> tags;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "TAG_RULE_ID")
  @JsonFormat(shape = JsonFormat.Shape.OBJECT)
  private TagRule tagRule;

  public BankTransaction() {
  }

  public BankTransaction(LocalDate transactiondate, String description, Double cost) {
    this.transactiondate = transactiondate;
    this.description = description;
    this.cost = cost;
  }

  public LocalDate getTransactiondate() {
    return transactiondate;
  }

  public void setTransactiondate(LocalDate transactiondate) {
    this.transactiondate = transactiondate;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(Double cost) {
    this.cost = cost;
  }

  public TagRule getTagRule() {
    return tagRule;
  }

  public BankTransaction setTagRule(TagRule tagRule) {
    this.tagRule = tagRule;
    return this;
  }

  public List<String> getTags() {
    return (this.tagRule != null) ? this.tagRule.getTags() : Lists.newArrayList();
  }

  public BankTransaction setTags(ArrayList<String> tags) {
    this.tagRule.getTags().addAll(tags);
    return this;
  }

  public boolean containTags() {
    boolean containTags = false;
    if (tagRule != null) {
      containTags = (this.tagRule.getTags().size() > 0);
    }
    return containTags;
  }

  public boolean containTags(List tags) {
    return this.tagRule.getTags().containsAll(tags);
  }

  public boolean containTag(String tag) {
    return this.tagRule.getTags().contains(tag);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BankTransaction that = (BankTransaction) o;
    return Objects.equals(transactiondate, that.transactiondate)
        && Objects.equals(description, that.description)
        && Objects.equals(cost, that.cost)
        && Objects.equals(tagRule, that.tagRule);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactiondate, description, cost, tagRule);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("transactiondate", transactiondate)
        .add("description", description)
        .add("cost", cost)
        .toString();
  }

  @Override
  public int compareTo(BankTransaction that) {
    return ComparisonChain.start()
        .compare(this.transactiondate, that.transactiondate)
        .compare(this.description, that.description)
        .compare(this.cost, that.cost)
        .compare(this.tagRule, that.tagRule)
        .result();
  }
}
