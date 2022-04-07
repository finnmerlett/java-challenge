package io.carbonchain.hiring.java.domain;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class AssetTest {

  @Test()
  public void testNameMatches_NameMatchesTerm_ReturnsTrue() {
    Asset asset = new Asset("Some name", "Some country", "Some continent");

    assertTrue(asset.nameMatches("Some name"),
        "Asset should match because it has matching name");
  }

  @Test()
  public void testNameMatches_NameDoesNotMatchTerm_ReturnsFalse() {
    Asset asset = new Asset("Some name", "Some country", "Some continent");

    assertFalse(asset.nameMatches("Some other name"),
        "Asset should not match because doesn't have matching name");
  }

  @Test()
  public void testMatchingScopes_MatchesTerm_ReturnsScopes() {
    Asset asset = new Asset("Some name", "Some country", "Some continent");
    String[] expectedScopes = { "Some country", "Some continent" };

    assertTrue(Arrays.deepEquals(asset.matchingScopes("Some country"), expectedScopes),
        "Asset should return scopes array because it has a matching country");
    assertTrue(Arrays.deepEquals(asset.matchingScopes("Some country"), expectedScopes),
        "Asset should return scopes array because it has a matching continent");
  }
}
