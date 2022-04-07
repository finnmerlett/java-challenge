package io.carbonchain.hiring.java.domain;

public class Asset {

  private final String name;
  private final String country;
  private final String continent;

  public Asset(String name, String country, String continent) {
    this.name = name;
    this.country = country;
    this.continent = continent;
  }

  public String getName() {
    return this.name;
  }

  public boolean nameMatches(String term) {
    return name.equalsIgnoreCase(term);
  }

  public String[] getScopes() {
    // higher array index equals higher specificity
    return new String[] { continent, country, name, };
  }

  public String[] matchingScopes(String term) {
    if (name.equalsIgnoreCase(term)
        || country.equalsIgnoreCase(term)
        || continent.equalsIgnoreCase(term)) {
      return getScopes();
    } else {
      return null;
    }
  }
}
