package io.carbonchain.hiring.java.app.controller;

import io.carbonchain.hiring.java.ApplicationException;
import io.carbonchain.hiring.java.Controller;
import io.carbonchain.hiring.java.Request;
import io.carbonchain.hiring.java.domain.Asset;
import io.carbonchain.hiring.java.domain.AssetRepository;
import io.carbonchain.hiring.java.domain.Model;
import io.carbonchain.hiring.java.domain.ModelRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModelsController implements Controller {

  private final AssetRepository assetRepository;
  private final ModelRepository modelRepository;

  public ModelsController(AssetRepository assetRepository, ModelRepository modelRepository) {
    this.assetRepository = assetRepository;
    this.modelRepository = modelRepository;
  }

  public String search(Request request) throws ApplicationException {
    // Replace underscores with spaces - this allows multi-name scopes to be passed
    // in using underscores
    String commodity = request.get(0).replace('_', ' ');
    String requestedScopeIn = request.get(1); // String or null
    String requestedScope = requestedScopeIn == null ? null : requestedScopeIn.replace('_', ' ');

    Asset matchingAsset = null;
    Model matchingModel = null;

    if (requestedScope != null) {
      // Find an asset that matches the scope provided
      matchingAsset = assetRepository.findFirstByScope(requestedScope);
    }

    if (matchingAsset != null) {
      // Retrieve all the scopes from the asset (that matches the requested scope)
      List<String> foundScopes = Arrays.asList(matchingAsset.getScopes());
      // Find where the requested scope lies in the list of scopes (case-insensitive)
      int indexOfRequestedScope = foundScopes.stream().map(String::toLowerCase).collect(Collectors.toList())
          .indexOf(requestedScope.toLowerCase());

      // Higher index position in foundScopes equals higher specificity, so discard
      // all found scopes that are after the requested scope in the array
      String[] matchingScopes = foundScopes.subList(0, indexOfRequestedScope + 1).toArray(String[]::new);
      // Find the model that matches the smallest applicable scope
      matchingModel = modelRepository.findSmallestMatchingScope(commodity, matchingScopes);
    }
    if (matchingModel == null) {
      // If no model was found to match the scope (or if no scope was provided), find
      // the global model
      matchingModel = modelRepository.findGlobalByCommodity(commodity);
    }

    if (matchingModel == null) {
      // Now if no model was found, there must not even be a global scope for the
      // commodity. In this case, throw a descriptive error for the user
      throw new ApplicationException("Requested commodity not found");
    }

    Double emissionIntensity = matchingModel.getEmissionIntensity();
    String matchingCommodity = matchingModel.getCommodity();
    String matchingScope = matchingModel.getScope();
    // Did we find an exact match on the scope requested? (also true if no scope was
    // provided)
    boolean scopeMatchesRequested = requestedScope == null ? true : requestedScope.equalsIgnoreCase(matchingScope);

    // Get the name of the scope we are actually returning
    String scopeReturned = matchingScope == null ? "Global" : matchingScope;
    // Let the user know if an imperfect match is being returned
    String mismatchWarning = scopeMatchesRequested ? ""
        : "Scope '" + requestedScope + "' for " + matchingCommodity + " not found. Closest match: ";

    return mismatchWarning
        + scopeReturned + " emission intensity for " + matchingCommodity + " is " + emissionIntensity;
  }

  // I wasn't sure what situations would require averaging models, and also I was
  // running out of time - so I just commented out this part

  // private Double averageModels(List<Model> models) {
  // Double emissionIntensity =
  // models.stream().map(Model::getEmissionIntensity).reduce(0.0, Double::sum);
  // return emissionIntensity / models.size();
  // }
}
