package uk.gov.ons.ctp.integration.common.product;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.godaddy.logging.Logger;
import com.godaddy.logging.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import uk.gov.ons.ctp.common.error.CTPException;
import uk.gov.ons.ctp.common.error.CTPException.Fault;
import uk.gov.ons.ctp.integration.common.product.model.Product;
import uk.gov.ons.ctp.integration.common.product.model.Product.CaseType;
import uk.gov.ons.ctp.integration.common.product.model.Product.Region;
import uk.gov.ons.ctp.integration.common.product.model.Product.RequestChannel;

@Component
public class ProductReference {

  private static final Logger log = LoggerFactory.getLogger(ProductReference.class);

  @Value("${productsJsonPath:classpath:/products.json}")
  Resource productFile;

  private List<Product> products;

  @PostConstruct
  public void initProducts() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      products =
          objectMapper.readValue(
              productFile.getInputStream(), new TypeReference<List<Product>>() {});

      Collection<Product> uniqueByFulfilmentCode =
          products
              .stream()
              .collect(Collectors.toMap(Product::getFulfilmentCode, p -> p, (p, q) -> p))
              .values();

      if (products.size() != uniqueByFulfilmentCode.size()) {
        throw new CTPException(
            Fault.SYSTEM_ERROR, "Product data set not unique by fulfilment code");
      }

      // the JSON could contain null list items
      // rather than over complicate the searchProducts filter
      // replace null lists with empty lists
      products
          .stream()
          .filter(p -> p.getRequestChannels() == null)
          .forEach(p -> p.setRequestChannels(new ArrayList<RequestChannel>()));
      products
          .stream()
          .filter(p -> p.getRegions() == null)
          .forEach(p -> p.setRegions(new ArrayList<Region>()));
      products
          .stream()
          .filter(p -> p.getCaseTypes() == null)
          .forEach(p -> p.setCaseTypes(new ArrayList<CaseType>()));

    } catch (JsonParseException e) {
      throw new CTPException(
          Fault.SYSTEM_ERROR, "Failed to parse common product reference dataset");
    } catch (JsonMappingException e) {
      throw new CTPException(Fault.SYSTEM_ERROR, "Failed to map common product reference dataset");
    } catch (IOException e) {
      throw new CTPException(Fault.SYSTEM_ERROR, "Failed to read common product reference dataset");
    }
  }

  @Cacheable("productCache")
  public List<Product> searchProducts(Product example) throws CTPException {
    return products
        .stream()
        .filter(
            p ->
                (example.getCaseTypes() == null
                        ? true
                        : p.getCaseTypes().containsAll(example.getCaseTypes()))
                    && (example.getProductGroup() == null
                        ? true
                        : p.getProductGroup().equals(example.getProductGroup()))
                    && (example.getHandler() == null
                        ? true
                        : p.getHandler().equals(example.getHandler()))
                    && (example.getIndividual() == null
                        ? true
                        : p.getIndividual().equals(example.getIndividual()))
                    && (example.getLanguage() == null
                        ? true
                        : p.getLanguage().equals(example.getLanguage()))
                    && (example.getFulfilmentCode() == null
                        ? true
                        : p.getFulfilmentCode().equals(example.getFulfilmentCode()))
                    && (example.getInitialContactCode() == null
                        ? true
                        : p.getInitialContactCode().equals(example.getInitialContactCode()))
                    && (example.getReminderContactCode() == null
                        ? true
                        : p.getReminderContactCode().equals(example.getReminderContactCode()))
                    && (example.getFieldDistributionCode() == null
                        ? true
                        : p.getFieldDistributionCode().equals(example.getFieldDistributionCode()))
                    && (example.getFieldQuestionnaireCode() == null
                        ? true
                        : p.getFieldQuestionnaireCode().equals(example.getFieldQuestionnaireCode()))
                    && (example.getDeliveryChannel() == null
                        ? true
                        : p.getDeliveryChannel().equals(example.getDeliveryChannel()))
                    && (example.getRegions() == null
                        ? true
                        : p.getRegions().containsAll(example.getRegions()))
                    && (example.getRequestChannels() == null
                        ? true
                        : p.getRequestChannels().containsAll(example.getRequestChannels()))
                    && (example.getDescription() == null
                        ? true
                        : p.getDescription().equals(example.getDescription())))
        .collect(Collectors.toList());
  }
}
