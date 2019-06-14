package ua.nure.library.web.controller.ajax.manager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Artem Kudria
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AjaxChangeManager {

  private String login;
  private String oldPassword;
  private String password;
  private String email;
  private String msq;
}
