package uk.gov.ons.ctp.integration.common.product;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.gov.ons.ctp.integration.common.product.model.Product;
import uk.gov.ons.ctp.integration.common.product.model.Product.CaseType;
import uk.gov.ons.ctp.integration.common.product.model.Product.Handler;

/**
 * Runs the core tests against the test json as well as additional tests below
 *
 * @author philwhiles
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = {"productsJsonPath=classpath:/testProducts.json"})
public class TestProductReferenceTest extends ProductReferenceTest {

  @Test
  public void onlyQMHandlerButCount() throws Exception {
    Product example = new Product();
    example.setHandler(Handler.QM);
    List<Product> products = productReference.searchProducts(example);
    // only test will always have two - prod json will vary over time!!!
    assertTrue(products.size() == 2);
    for (Product p : products) {
      assertTrue(p.getHandler().equals(Handler.QM));
    }
  }

  @Test
  public void twoCaseTypes() throws Exception {
    Product example = new Product();
    example.setCaseTypes(Arrays.asList(CaseType.HH, CaseType.CE));
    List<Product> products = productReference.searchProducts(example);
    assertTrue(products.size() == 1);
    assertTrue(products.get(0).getFulfilmentCode().equals("2CASETYPES"));
  }
}
