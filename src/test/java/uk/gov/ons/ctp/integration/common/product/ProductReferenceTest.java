package uk.gov.ons.ctp.integration.common.product;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import uk.gov.ons.ctp.integration.common.product.model.Product;
import uk.gov.ons.ctp.integration.common.product.model.Product.CaseType;
import uk.gov.ons.ctp.integration.common.product.model.Product.DeliveryChannel;
import uk.gov.ons.ctp.integration.common.product.model.Product.Handler;
import uk.gov.ons.ctp.integration.common.product.model.Product.Region;
import uk.gov.ons.ctp.integration.common.product.model.Product.RequestChannel;

/**
 * THese are the core product tests that will be run against both the test json and the production
 * json, by the appropriate sub classes
 *
 * @author philwhiles
 */
@ContextConfiguration(classes = {ProductReference.class})
public abstract class ProductReferenceTest {

  @Autowired ProductReference productReference;

  @Test
  public void onlyHouseholdAndNotIndividual() throws Exception {
    Product example = new Product();
    example.setCaseTypes(Arrays.asList(CaseType.HH));
    example.setIndividual(false);
    List<Product> products = productReference.searchProducts(example);
    assertTrue(products.size() > 0);
    for (Product p : products) {
      assertTrue(p.getCaseTypes().contains(CaseType.HH));
      assertTrue(p.getIndividual().equals(false));
    }
  }

  @Test
  public void onlyHousehold() throws Exception {
    Product example = new Product();
    example.setCaseTypes(Arrays.asList(CaseType.HH));
    List<Product> products = productReference.searchProducts(example);
    assertTrue(products.size() > 0);
    boolean indivFalse = false;
    boolean indivTrue = false;
    for (Product p : products) {
      assertTrue(p.getCaseTypes().contains(CaseType.HH));
      if (p.getIndividual()) {
        indivTrue = true;
      } else {
        indivFalse = true;
      }
    }
    assertTrue(indivFalse & indivTrue);
  }

  @Test
  public void onlyQMHandler() throws Exception {
    Product example = new Product();
    example.setHandler(Handler.QM);
    List<Product> products = productReference.searchProducts(example);
    assertTrue(products.size() > 0);
    for (Product p : products) {
      assertTrue(p.getHandler().equals(Handler.QM));
    }
  }

  @Test
  public void onlyFieldLanguageE() throws Exception {
    Product example = new Product();
    example.setLanguage(Product.Language.E);
    List<Product> products = productReference.searchProducts(example);
    assertTrue(products.size() > 0);
    for (Product p : products) {
      assertTrue(p.getLanguage().equals(Product.Language.E));
    }
  }

  @Test
  public void onlyRequestChannelCC() throws Exception {
    Product example = new Product();
    example.setRequestChannels(Arrays.asList(RequestChannel.CC));
    List<Product> products = productReference.searchProducts(example);
    assertTrue(products.size() > 0);
    for (Product p : products) {
      assertTrue(p.getRequestChannels().contains(RequestChannel.CC));
    }
  }

  @Test
  public void onlyIndividual() throws Exception {
    Product example = new Product();
    example.setIndividual(true);
    assertOnlyExpectedIndividuality(example);
  }

  void assertOnlyExpectedIndividuality(Product example) throws Exception {
    List<Product> products = productReference.searchProducts(example);
    assertTrue(products.size() > 0);
    for (Product p : products) {
      assertTrue(p.getIndividual() == example.getIndividual());
    }
  }

  void assertOnlyExpectedCaseTypes(Product example) throws Exception {
    List<Product> products = productReference.searchProducts(example);
    for (Product p : products) {
      assertTrue(p.getCaseTypes().containsAll(example.getCaseTypes()));
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
  public void allNIreland() throws Exception {
    Product example = new Product();
    example.setRegions(Arrays.asList(Region.N));
    assertExpectedRegion(example);
  }

  void assertExpectedRegion(Product example) throws Exception {
    List<Product> products = productReference.searchProducts(example);
    assertTrue(products.size() > 0);
    for (Product p : products) {
      assertTrue(p.getRegions().containsAll(example.getRegions()));
    }
  }

  @Test
  public void justOneByCode() throws Exception {
    Product example = new Product();
    example.setFulfilmentCode("P_LP_HL4");
    List<Product> products = productReference.searchProducts(example);
    assertTrue(products.size() == 1);
    assertTrue(products.get(0).getFulfilmentCode().equals("P_LP_HL4"));
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
    assertTrue(products.size() > 0);
    for (Product p : products) {
      assertTrue(p.getDeliveryChannel().equals(example.getDeliveryChannel()));
    }
  }
}
