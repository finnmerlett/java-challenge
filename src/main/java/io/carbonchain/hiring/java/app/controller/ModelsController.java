package io.carbonchain.hiring.java.app.controller;

import io.carbonchain.hiring.java.ApplicationException;
import io.carbonchain.hiring.java.Controller;
import io.carbonchain.hiring.java.Request;
import io.carbonchain.hiring.java.domain.AssetRepository;
import io.carbonchain.hiring.java.domain.Model;
import io.carbonchain.hiring.java.domain.ModelRepository;
import java.util.List;

public class ModelsController implements Controller {

  private final AssetRepository assetRepository;
  private final ModelRepository modelRepository;

  public ModelsController(AssetRepository assetRepository, ModelRepository modelRepository) {
    this.assetRepository = assetRepository;
    this.modelRepository = modelRepository;
  }

  public String search(Request request) throws ApplicationException {
    String commodity = request.get(0);

    Double emissionIntensity = modelRepository.findGlobalByCommodity(commodity).getEmissionIntensity();
    return "Global emission intensity for " + commodity + " is " + emissionIntensity;
  }

  private Double averageModels(List<Model> models) {
    Double emissionIntensity = models.stream().map(Model::getEmissionIntensity).reduce(0.0, Double::sum);
    return emissionIntensity / models.size();
  }
}
