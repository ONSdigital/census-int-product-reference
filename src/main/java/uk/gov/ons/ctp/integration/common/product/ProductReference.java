package uk.gov.ons.ctp.integration.common.product;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import uk.gov.ons.ctp.common.error.CTPException;
import uk.gov.ons.ctp.common.error.CTPException.Fault;
import uk.gov.ons.ctp.integration.common.product.model.Product;

@Component
public class ProductReference {

  @Value("classpath:products.json")
  Resource productFile;

  @Cacheable("productCache")
  public List<Product> searchProducts(Product example) throws CTPException {
    ObjectMapper objectMapper = new ObjectMapper();
    List<Product> products = null;
    try {
      products =
          objectMapper.readValue(productFile.getFile(), new TypeReference<List<Product>>() {});
    } catch (JsonParseException e) {
      throw new CTPException(
          Fault.SYSTEM_ERROR, "Failed to parse common product reference dataset");
    } catch (JsonMappingException e) {
      throw new CTPException(Fault.SYSTEM_ERROR, "Failed to map common product reference dataset");
    } catch (IOException e) {
      throw new CTPException(Fault.SYSTEM_ERROR, "Failed to read common product reference dataset");
    }

    return products
        .stream()
        .filter(
            p ->
                (example.getCaseType() == null
                        ? true
                        : p.getCaseType().equals(example.getCaseType()))
                    && (example.getHandler() == null
                        ? true
                        : p.getHandler().equals(example.getHandler()))
                    && (example.getProductCode() == null
                        ? true
                        : p.getProductCode().equals(example.getProductCode()))
                    && (example.getProductType() == null
                        ? true
                        : p.getProductType().equals(example.getProductType()))
                    && (example.getRegions() == null
                        ? true
                        : p.getRegions().containsAll(example.getRegions()))
                    && (example.getChannels() == null
                        ? true
                        : p.getChannels().containsAll(example.getChannels()))
                    && (example.getDescription() == null
                        ? true
                        : p.getDescription().equals(example.getDescription())))
        .collect(Collectors.toList());
  }
}
