package uk.gov.ons.ctp.integration.common.product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static uk.gov.ons.ctp.integration.common.product.CustomAsserts.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import uk.gov.ons.ctp.integration.common.product.model.Product;

public class CaseTypeTests {

  @Test
  public void toListReturnsASingletonList() {

    Arrays.stream(Product.CaseType.values())
        .forEach(
            c -> {
              List<Product.CaseType> list = c.toList();
              assertEquals(1, list.size());
              assertTrue(list.contains(c));
              assertThrows(
                  UnsupportedOperationException.class,
                  () -> {
                    list.add(Product.CaseType.CE);
                  });
            });
  }
}
