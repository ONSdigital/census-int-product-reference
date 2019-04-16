package uk.gov.ons.ctp.integration.common.product;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.gov.ons.ctp.integration.common.product.model.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ProductReference.class})
public class ProductReferenceTest {

  @Autowired private ProductReference productReference;

  @Test
  public void onlyHousehold() throws Exception {
    Product example = new Product();
    example.setCaseType("HH");
    assertOnlyExpectedCaseType(example);
  }

  @Test
  public void onlyIndividual() throws Exception {
    Product example = new Product();
    example.setCaseType("IND");
    assertOnlyExpectedCaseType(example);
  }

  private void assertOnlyExpectedCaseType(Product example) throws Exception {
    List<Product> products = productReference.searchProducts(example);
    for (Product p : products) {
      assertTrue(p.getCaseType().equals(example.getCaseType()));
    }
  }

  @Test
  public void allEngland() throws Exception {
    Product example = new Product();
    example.setRegions(Arrays.asList("E"));
    assertExpectedRegion(example);
  }

  @Test
  public void allWales() throws Exception {
    Product example = new Product();
    example.setRegions(Arrays.asList("W"));
    assertExpectedRegion(example);
  }

  @Test
  public void allWalesEngland() throws Exception {
    Product example = new Product();
    example.setRegions(Arrays.asList("W", "E"));
    assertExpectedRegion(example);
  }

  @Test
  public void allPolish() throws Exception {
    Product example = new Product();
    example.setLanguage("pol");
    assertOnlyExpectedLanguage(example);
  }

  private void assertOnlyExpectedLanguage(Product example) throws Exception {
    List<Product> products = productReference.searchProducts(example);
    for (Product p : products) {
      assertTrue(p.getLanguage().equals(example.getLanguage()));
    }
  }

  @Test
  public void allNIreland() throws Exception {
    Product example = new Product();
    example.setRegions(Arrays.asList("N"));
    assertExpectedRegion(example);
  }

  private void assertExpectedRegion(Product example) throws Exception {
    List<Product> products = productReference.searchProducts(example);
    for (Product p : products) {
      assertTrue(p.getRegions().containsAll(example.getRegions()));
    }
  }

  @Test
  public void justHC4() throws Exception {
    Product example = new Product();
    example.setProductCode("HC4");
    List<Product> products = productReference.searchProducts(example);
    for (Product p : products) {
      assertTrue(p.getProductCode().equals("HC4"));
    }
  }

  @Test
  public void justPaper() throws Exception {
    Product example = new Product();
    example.setDeliveryChannel("Paper");
    List<Product> products = productReference.searchProducts(example);
    for (Product p : products) {
      assertTrue(p.getDeliveryChannel().equals("Paper"));
    }
  }

  @Test
  public void allSMS() throws Exception {
    Product example = new Product();
    example.setRequestChannels(Arrays.asList("SMS"));
    assertExpectedChannel(example);
  }

  private void assertExpectedChannel(Product example) throws Exception {
    List<Product> products = productReference.searchProducts(example);
    for (Product p : products) {
      assertTrue(p.getRequestChannels().containsAll(example.getRequestChannels()));
    }
  }
}
