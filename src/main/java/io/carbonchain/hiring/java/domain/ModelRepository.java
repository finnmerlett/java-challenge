package io.carbonchain.hiring.java.domain;

public class ModelRepository {

  private final Model[] models;

  public ModelRepository(Model[] models) {
    this.models = models;
  }

  public Model findGlobalByCommodity(String commodity) {
    for (Model model : models) {
      if (model.isGlobalForCommodity(commodity)) {
        return model;
      }
    }
    return null;
  }
}
