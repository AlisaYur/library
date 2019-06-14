package ua.nure.library.web.controller.ajax.genre.validation;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Artem Kudria
 */
@NoArgsConstructor
@Data
public class AjaxGenreData implements Serializable {

  private static final long serialVersionUID = -5384437079757834128L;

  private String name;
}
