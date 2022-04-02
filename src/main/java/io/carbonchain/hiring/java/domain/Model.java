package io.carbonchain.hiring.java.domain;

public class Model {

  private final String commodity;
  private final String scope;
  private final Double emissionIntensity;

  public Model(String commodity, String scope, Double emissionIntensity) {
    this.commodity = commodity;
    this.scope = scope;
    this.emissionIntensity = emissionIntensity;
  }

  public boolean isGlobalForCommodity(String commodity) {
    return this.commodity.equals(commodity) && scope == null;
  }

  public Double getEmissionIntensity() {
    return emissionIntensity;
  }
}
