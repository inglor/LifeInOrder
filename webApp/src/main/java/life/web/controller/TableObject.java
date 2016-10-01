package life.web.controller;

import java.util.List;

public class TableObject {

  private final String date;
  private final String description;
  private final Double cost;
  private final List<String> tags;

  private TableObject(Builder builder) {
    this.date = builder.date;
    this.description = builder.description;
    this.cost = builder.cost;
    this.tags = builder.tags;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getDate() {
    return date;
  }

  public String getDescription() {
    return description;
  }

  public Double getCost() {
    return cost;
  }

  public List<String> getTags() {
    return tags;
  }

  public static final class Builder {
    private String date;
    private String description;
    private Double cost;
    private List<String> tags;

    private Builder() {
    }

    public Builder date(String date) {
      this.date = date;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder cost(Double cost) {
      this.cost = cost;
      return this;
    }

    public Builder tags(List<String> tags) {
      this.tags = tags;
      return this;
    }

    public TableObject build() {
      return new TableObject(this);
    }
  }
}
