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
import uk.gov.ons.ctp.integration.common.product.model.Product.CaseType;
import uk.gov.ons.ctp.integration.common.product.model.Product.DeliveryChannel;
import uk.gov.ons.ctp.integration.common.product.model.Product.Region;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ProductReference.class})
public class ProductReferenceTest {

  @Autowired private ProductReference productReference;

  @Test
  public void onlyHousehold() throws Exception {
    Product example = new Product();
    example.setCaseType(CaseType.H);
    assertOnlyExpectedCaseType(example);
  }

  @Test
  public void onlyIndividual() throws Exception {
    Product example = new Product();
    example.setCaseType(CaseType.HI);
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
    example.setRegions(Arrays.asList(Region.E));
    assertExpectedRegion(example);
  }

  @Test
  public void allWales() throws Exception {
    Product example = new Product();
    example.setRegions(Arrays.asList(Region.W));
    assertExpectedRegion(example);
  }

  @Test
  public void allWalesEngland() throws Exception {
    Product example = new Product();
    example.setRegions(Arrays.asList(Region.E, Region.W));
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
    example.setRegions(Arrays.asList(Region.E));
    assertExpectedRegion(example);
  }

  private void assertExpectedRegion(Product example) throws Exception {
    List<Product> products = productReference.searchProducts(example);
    assertTrue(products.size() > 0);
    for (Product p : products) {
      assertTrue(p.getRegions().containsAll(example.getRegions()));
    }
  }

  @Test
  public void justOneByCode() throws Exception {
    Product example = new Product();
    example.setFulfilmentCode("P_TB_TBTUR1");
    List<Product> products = productReference.searchProducts(example);
    assertTrue(products.size() == 1);
    assertTrue(products.get(0).getFulfilmentCode().equals("P_TB_TBTUR1"));
  }

  @Test
  public void justPaper() throws Exception {
    Product example = new Product();
    example.setDeliveryChannel(DeliveryChannel.POST);
    List<Product> products = productReference.searchProducts(example);
    assertTrue(products.size() > 0);
    for (Product p : products) {
      assertTrue(p.getDeliveryChannel().equals(DeliveryChannel.POST));
    }
  }

  @Test
  public void allSMS() throws Exception {
    Product example = new Product();
    example.setDeliveryChannel(DeliveryChannel.SMS);
    List<Product> products = productReference.searchProducts(example);
    for (Product p : products) {
      assertTrue(p.getDeliveryChannel().equals(example.getDeliveryChannel()));
    }
  }
}
