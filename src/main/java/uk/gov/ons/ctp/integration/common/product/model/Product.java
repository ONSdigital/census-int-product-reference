package uk.gov.ons.ctp.integration.common.product.model;

import java.util.List;
import lombok.Data;

@Data
public class Product {
  private String productCode;
  private String description;
  private String caseType;
  private List<String> regions;
  private String productType;
  private List<String> channels;
  private String handler;
}
