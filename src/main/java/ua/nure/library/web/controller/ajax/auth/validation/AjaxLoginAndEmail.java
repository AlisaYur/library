package ua.nure.library.web.controller.ajax.auth.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Artem Kudria
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AjaxLoginAndEmail {

  private String login;
  private String email;
}
