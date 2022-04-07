package io.carbonchain.hiring.java.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssetRepositoryTest {
  private static AssetRepository assetRepo = new AssetRepository(new Asset[] {
      new Asset("Khetri", "India", "Asia"),
      new Asset("Cerro Verde", "Peru", "South America"),
      new Asset("El Abra", "Chile", "South America"),
      new Asset("Red Dog", "USA", "North America"),
      new Asset("Antamina", "Peru", "South America"),
      new Asset("Tara", "Ireland", "Europe"),
  });

  @Test
  public void testFindFirstMatchingAsset_MatchingAssetExists_ReturnsFirstAssetWithCorrectValues() {
    Assertions.assertEquals(assetRepo.findFirstByScope("USA").getName(), "Red Dog",
        "expected asset name should be 'Red Dog' because 'USA' matches that asset's scopes");
    Assertions.assertEquals(assetRepo.findFirstByScope("South America").getName(), "Cerro Verde",
        "expected asset name should be 'Cerro Verde' because it is the first matching asset for 'South America'");
  }
}
