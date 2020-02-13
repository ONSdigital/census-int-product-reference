package uk.gov.ons.ctp.integration.common.product;

import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.gov.ons.ctp.integration.common.product.model.Product;

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
    assertTrue(products.size() == 56);
  }
}
