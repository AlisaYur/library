package ua.nure.library.web.controller.ajax.reader.penalty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Artem Kudria
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseLiqPay {

  private String status;
  private String order_id;
}
