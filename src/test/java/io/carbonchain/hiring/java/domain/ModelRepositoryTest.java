package io.carbonchain.hiring.java.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

public class ModelRepositoryTest {
  private static ModelRepository modelRepo = new ModelRepository(new Model[] {
      new Model("Copper", null, 13.4),
      new Model("Copper", "India", 18.223),
      new Model("Copper", "Chile", 9.23),
      new Model("Copper", "South America", 11.12),
      new Model("Zinc", null, 5.33),
      new Model("Zinc", "USA", 3.45),
      new Model("Zinc", "North America", 3.98),
      new Model("Zinc", "South America", 6.13),
      new Model("Iron", "USA", 11.19),
      new Model("Cadmium", null, 34.55),
  });

  private void shuffleModels() {
    List<Model> modelList = Arrays.asList(modelRepo.getModels());
    Collections.shuffle(modelList);
    modelRepo = new ModelRepository(modelList.toArray(new Model[modelList.size()]));
  }

  @BeforeEach
  void beforeEachTest() {
    shuffleModels();
  }

  @RepeatedTest(10)
  public void testFindGlobalByCommodity_CommodityExistsWithNullScope_ReturnsModelWithCorrectValue() {
    Assertions.assertEquals(modelRepo.findGlobalByCommodity("Copper").getEmissionIntensity(), 13.4,
        "Emission intensity value of returned model should match Copper value, since Copper model exists with null scope");
  }

  @RepeatedTest(10)
  public void testFindGlobalByCommodity_CommodityExistsButOnlyWithScope_ReturnsNull() {
    Assertions.assertNull(modelRepo.findGlobalByCommodity("Iron"),
        "null value should be returned, since no null scope iron model exists");
  }

  @RepeatedTest(10)
  public void testFindSmallestScopeByCommodity_CommodityExistsWithSmallScope_ReturnsModelWithCorrectValue() {
    String[] providedScopes = { "South America", "Chile" };
    Model foundModel = modelRepo.findSmallestMatchingScope("Copper", providedScopes);
    Assertions.assertNotNull(foundModel);
    Assertions.assertEquals(foundModel.getEmissionIntensity(), 9.23,
        "Emission intensity value of returned model should match copper value of Chile, since that is the smallest matching scope");
  }

  @RepeatedTest(10)
  public void testFindSmallestScopeByCommodity_CommodityOnlyExistsWithLargeScope_ReturnsModelWithCorrectValue() {
    String[] providedScopes = { "South America", "Chile" };
    Model foundModel = modelRepo.findSmallestMatchingScope("Zinc", providedScopes);
    Assertions.assertNotNull(foundModel);
    Assertions.assertEquals(foundModel.getEmissionIntensity(), 6.13,
        "Emission intensity value of returned model should match zinc value of South America, since it matches and no smaller scope exists");
  }

  @RepeatedTest(10)
  public void testFindSmallestScopeByCommodity_ScopedCommodityDoesntExist_ReturnsNull() {
    String[] providedScopes = { "South America", "Chile" };
    Model foundModel = modelRepo.findSmallestMatchingScope("Cadmium", providedScopes);
    Assertions.assertNull(foundModel);
  }
}
