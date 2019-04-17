package uk.gov.ons.ctp.integration.common.product.model;

import java.util.List;
import lombok.Data;

@Data
public class Product {
  private String fulfilmentCode;
  private String description;
  private String language;
  private CaseType caseType;
  private List<Region> regions;
  private DeliveryChannel deliveryChannel;
  private List<RequestChannel> requestChannels;
  private Handler handler;

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
}
