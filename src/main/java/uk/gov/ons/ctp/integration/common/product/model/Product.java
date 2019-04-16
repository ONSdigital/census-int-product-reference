package uk.gov.ons.ctp.integration.common.product.model;

import java.util.List;
import lombok.Data;

@Data
public class Product {
  private String productCode;
  private String description;
  private String language;
  private String caseType;
  private List<String> regions;
  private String deliveryChannel;
  private List<String> requestChannels;
  private String handler;
}
