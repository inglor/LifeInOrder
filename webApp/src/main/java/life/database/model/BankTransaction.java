package life.database.model;

import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fragkakise on 11/09/2015.
 */
@Entity
@Table(name = "BANKTRANSACTION")
public class BankTransaction implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "TRANSACTIONDATE", nullable = false)
  private LocalDate transactiondate;

  @Column(name = "DESCRIPTION", nullable = false)
  private String description;

  @Column(name = "COST", nullable = false)
  private Double cost;

  @Column(name = "TAGS", nullable = false)
  private List<BankTransactionTag> tags;

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

  public BankTransaction() {
  }

  public BankTransaction(LocalDate transactiondate, String description, double cost, List<BankTransactionTag> bankTransactionTags) {
    this.transactiondate = transactiondate;
    this.description = description;
    this.cost = cost;
    this.tags = bankTransactionTags;
  }

  public BankTransaction(LocalDate transactiondate, String description, Double cost) {
    this.transactiondate = transactiondate;
    this.description = description;
    this.cost = cost;
    this.tags = new ArrayList<>();
  }

  @Override
  public boolean equals(Object o) {
    if(this == o) return true;
    if(o == null || getClass() != o.getClass()) return false;
    if(o instanceof BankTransaction) {
      BankTransaction cmpBankTransaction = (BankTransaction)o;
      if((transactiondate != null) && (cmpBankTransaction.transactiondate != null) && transactiondate.equals(cmpBankTransaction.transactiondate)) {
        if((description != null) && (cmpBankTransaction.description != null) && description.equals(cmpBankTransaction.description)
                && (cost != null) && cmpBankTransaction.cost !=null && cost.equals(cmpBankTransaction.cost) ) {
           return true;
        }
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = transactiondate.hashCode();
    result = 31 * result + description.hashCode();
    temp = Double.doubleToLongBits(cost);
    result = 31 * result + (int)(temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public String toString() {
    return "BankTransaction { " +
             "transactiondate= " + transactiondate +
             ", description= '" + description + '\'' +
             ", cost= " + cost +
             '}';
  }

  public boolean hasTags() {
    return (tags.size() > 0);
  }

  public List<BankTransactionTag> getTags() {
    return tags;
  }

  public void setTag(BankTransactionTag tag) {
    this.tags.add(tag);
  }
}
