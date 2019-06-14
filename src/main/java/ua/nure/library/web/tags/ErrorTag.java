package ua.nure.library.web.tags;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import lombok.extern.log4j.Log4j;

/**
 * @author Artem Kudria
 */
@Log4j
public class ErrorTag extends TagSupport {

  @Override
  public int doStartTag() {
    JspWriter out = pageContext.getOut();
    try {
      String errorMessage = pageContext.getRequest().getParameter("error");
      if (errorMessage != null) {
        out.print("<div id='error-message' class='error-message'>" + errorMessage + "</div>");
      }
    } catch (Exception e) {
      log.debug("Error show error in tld tag!");
    }
    return SKIP_BODY;
  }
}
