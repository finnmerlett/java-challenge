package io.carbonchain.hiring.java.domain;

public class AssetRepository {

  private final Asset[] assets;

  public AssetRepository(Asset[] assets) {
    this.assets = assets;
  }

  public Asset findByName(String name) {
    for (Asset asset : assets) {
      if (asset.nameMatches(name)) {
        return asset;
      }
    }
    return null;
  }
}
