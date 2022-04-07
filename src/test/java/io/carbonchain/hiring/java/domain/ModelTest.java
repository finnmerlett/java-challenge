package io.carbonchain.hiring.java.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ModelTest {

  @Test()
  public void testIsGlobalForCommodity_CommodityMatchesAndScopeIsNull_ReturnsTrue() {
    Model model = new Model("Copper", null, 12.34);

    assertTrue(model.isGlobalForCommodity("Copper"),
        "Model should be global because is matches commodity and has null scope");
  }

  @Test()
  public void testIsGlobalForCommodity_CommodityMatchesButScopeIsNotNull_ReturnsFalse() {
    Model model = new Model("Copper", "India", 12.34);

    assertFalse(model.isGlobalForCommodity("Copper"),
        "Model should not be global because it has non-null scope");
  }

  @Test()
  public void testIsGlobalForCommodity_ScopeIsNullButCommodityDoesNotMatch_ReturnsFalse() {
    Model model = new Model("Copper", null, 12.34);

    assertFalse(model.isGlobalForCommodity("Zinc"),
        "Model should not be global for the commodity because the commodity doesn't match");
  }

  @Test()
  public void testIsScopedForCommodity_CommodityMatchesAndScope_ReturnsTrue() {
    Model model = new Model("Copper", "India", 12.34);

    assertTrue(model.matchesCommodityAndScope("Copper", "India"),
        "Model should match because it matches the commodity and scope provided");
  }

  @Test()
  public void testIsScopedForCommodity_CommodityMatchesAndNotScope_ReturnsFalse() {
    Model model = new Model("Copper", "India", 12.34);

    assertFalse(model.matchesCommodityAndScope("Copper", "Chile"),
        "Model should not match because it matches the commodity but not the scope provided");
  }
}
