package uk.gov.ons.ctp.integration.common.product;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.gov.ons.ctp.integration.common.product.model.Product;
import uk.gov.ons.ctp.integration.common.product.model.Product.CaseType;
import uk.gov.ons.ctp.integration.common.product.model.Product.Region;

/**
 * Runs the core tests against the production json, as well as additional tests specified below
 *
 * @author philwhiles
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MainProductReferenceTest extends ProductReferenceTest {

  @Test
  public void totalProductsCheck() throws Exception {
    Product example = new Product();
    List<Product> products = productReference.searchProducts(example);
    assertEquals(121, products.size());
  }

  @Test
  public void mytotalProductsCheck() throws Exception {
    Product example = new Product();
    example.setCaseTypes(Arrays.asList(CaseType.HH));
    example.setRegions(Arrays.asList(Region.E));
    example.setIndividual(true);

    List<Product> products = productReference.searchProducts(example);
    assertEquals(4, products.size());
  }
}
