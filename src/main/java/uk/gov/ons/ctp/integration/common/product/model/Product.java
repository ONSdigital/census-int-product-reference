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
    RH,
    EQ
  }

  public enum DeliveryChannel {
    POST,
    SMS,
  }

  public enum Region {
    E,
    W,
    N
  }

  public enum CaseType {
    HH,
    CE,
    SPG
  }

  public enum Handler {
    QM,
    NOTIFY,
    PRINT
  }

  public enum ProductGroup {
    EASY_READ,
    INVITE,
    QUESTIONNAIRE,
    CONTINUATION,
    UAC,
    LARGE_PRINT,
    TRANSLATION
  }

  public enum Language {
    E,
    W,
    B
  }

  // NOTE : fulfilementCode must be unique within the Product data set
  private String fulfilmentCode;
  private ProductGroup productGroup;
  private String description;
  private List<CaseType> caseTypes;
  private Boolean individual;
  private List<Region> regions;
  private DeliveryChannel deliveryChannel;
  private List<RequestChannel> requestChannels;
  private Handler handler;
  private Language language;
}
