package ua.nure.library.web.controller.ajax.reader.ajax;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Artem Kudria
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AjaxCountOrderBody {

  private String login;
  private Integer allCount;
  private Integer activeCount;
  private Integer penaltyCount;
}
