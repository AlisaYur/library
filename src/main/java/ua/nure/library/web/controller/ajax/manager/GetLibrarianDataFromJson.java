package ua.nure.library.web.controller.ajax.manager;

import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import ua.nure.library.web.controller.ajax.AjaxConstants;

/**
 * @author Artem Kudria
 */
public final class GetLibrarianDataFromJson {

  private GetLibrarianDataFromJson() {
  }

  public static AjaxChangeManager getChangeLibrarian(HttpServletRequest request) {
    Gson gson = new Gson();
    String ajaxFromRequest = request.getParameter(AjaxConstants.REQUEST_DATA);
    return gson.fromJson(ajaxFromRequest, AjaxChangeManager.class);
  }
}
