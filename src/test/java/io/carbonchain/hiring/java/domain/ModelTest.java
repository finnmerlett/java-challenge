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
}
