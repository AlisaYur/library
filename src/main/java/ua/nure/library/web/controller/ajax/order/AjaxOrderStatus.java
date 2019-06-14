package ua.nure.library.web.controller.ajax.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Artem Kudria
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AjaxOrderStatus {

  private Long orderId;
  private String newStatus;
  private String msq;
}
