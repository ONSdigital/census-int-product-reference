package uk.gov.ons.ctp.integration.common.product.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

  public enum RequestChannel {
    CC,
    FIELD,
    RH
  }

  public enum DeliveryChannel {
    POST,
    SMS,
    EMAIL
  }

  public enum Region {
    E,
    W,
    N
  }

  public enum CaseType {
    H,
    HI,
    C,
    CI
  }

  public enum Handler {
    QM,
    NOTIFY,
    PRINT
  }

  // NOTE : fulfilementCode must be unique within the Product data set
  private String fulfilmentCode;
  private String initialContactCode;
  private String reminderContactCode;
  private String fieldDistributionCode;
  private String description;
  private String language;
  private CaseType caseType;
  private List<Region> regions;
  private DeliveryChannel deliveryChannel;
  private List<RequestChannel> requestChannels;
  private Handler handler;
}
