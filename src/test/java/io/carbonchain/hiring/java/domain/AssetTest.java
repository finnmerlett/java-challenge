package io.carbonchain.hiring.java.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssetTest {

  @Test()
  public void testNameMatches_NameMatchesTerm_ReturnsTrue() {
    Asset asset = new Asset("Some name", "Some country", "Some continent");

    Assertions.assertTrue(asset.nameMatches("Some name"),
        "Asset should match because it has matching name");
  }

  @Test()
  public void testNameMatches_NameDoesNotMatchTerm_ReturnsFalse() {
    Asset asset = new Asset("Some name", "Some country", "Some continent");

    Assertions.assertFalse(asset.nameMatches("Some other name"),
        "Asset should not match because doesn't have matching name");
  }

  @Test()
  public void testGetScopes_ReturnsScopes() {
    Asset asset = new Asset("Some name", "Some country", "Some continent");
    // expected scopes in increasing order of specificity
    String[] expectedScopes = { "Some continent", "Some country", "Some name", };

    Assertions.assertArrayEquals(asset.getScopes(), expectedScopes,
        "Asset should return correct scopes array");
  }

  @Test()
  public void testMatchingScopes_MatchesTerm_ReturnsScopes() {
    Asset asset = new Asset("Some name", "Some country", "Some continent");
    // expected scopes in increasing order of specificity
    String[] expectedScopes = { "Some continent", "Some country", "Some name", };

    Assertions.assertArrayEquals(asset.matchingScopes("Some country"), expectedScopes,
        "Asset should return scopes array because it has a matching country");
    Assertions.assertArrayEquals(asset.matchingScopes("Some continent"), expectedScopes,
        "Asset should return scopes array because it has a matching continent");
  }
}
