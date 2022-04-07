package io.carbonchain.hiring.java.domain;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class ModelRepository {

  private final Model[] models;

  public ModelRepository(Model[] models) {
    this.models = models;
  }

  public Model[] getModels() {
    return this.models;
  }

  public Model findGlobalByCommodity(String commodity) {
    for (Model model : models) {
      if (model.isGlobalForCommodity(commodity)) {
        return model;
      }
    }
    return null;
  }

  public Model findSmallestMatchingScope(String commodity, String[] scopes) {
    // Find the first model that matches a scope. Assumes no duplicate scopes
    Function<String, Model> getModelMatchingScope = s -> Stream.of(models)
        .filter(m -> m.matchesCommodityAndScope(commodity, s)).findAny().orElse(null);

    // Turn array of scopes into array of matching models (and filter out nulls)
    Model[] matchingModels = Stream.of(scopes)
        .map(getModelMatchingScope)
        .filter(Objects::nonNull)
        .toArray(Model[]::new);

    if (matchingModels.length == 0) {
      // Return null if no matching models
      return null;
    } else {
      // Return last (most specific, aka smallest scope) matching model, if any found
      return matchingModels[matchingModels.length - 1];
    }
  }
}
