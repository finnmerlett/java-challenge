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

  public boolean nameMatches(String term) {
    return name.equals(term);
  }

  public String[] matchingScopes(String term) {
    if (country.equals(term) || continent.equals(term)) {
      return new String[] { country, continent };
    } else {
      return null;
    }
  }
}
