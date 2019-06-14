package ua.nure.library.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j;
import ua.nure.library.model.role.entity.Role;
import ua.nure.library.model.role.entity.RoleName;
import ua.nure.library.util.ConsEncoding;
import ua.nure.library.util.Path;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

@Log4j
@WebFilter(urlPatterns = {"/*"}, filterName = "AuthFilter")
public class AuthFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
    filterPath(request, response, chain);
  }

  private void filterPath(ServletRequest request, ServletResponse response, FilterChain chain) {
    HttpServletRequest servletRequest = (HttpServletRequest) request;
    HttpServletResponse servletResponse = (HttpServletResponse) response;
    HttpSession httpSession = servletRequest.getSession(false);
    response.setContentType(ConsEncoding.UTF_8);

    boolean loggedIn = httpSession != null && httpSession.getAttribute("user") != null;
    boolean activeRole = httpSession != null && httpSession.getAttribute("roles") != null;
    boolean access = false;
    String path = Path.MAIN_MENU_ACTION + servletRequest.getParameter("action");

    boolean isGuard = checkPath(path);
    access = isAccess(httpSession, activeRole, access, path);
    boolean loginRequest = path.equals(Path.SIGN_IN_PAGE) || path.equals(Path.SIGN_IN);
    boolean singUpRequest = path.equals(Path.SIGN_UP) || path.equals(Path.SIGN_IN_PAGE);

    try {
      LogginParam logginParam = new LogginParam(loggedIn, isGuard, access, loginRequest,
          singUpRequest);
      redirectFilter(logginParam, servletRequest, servletResponse, chain, servletResponse);
    } catch (IOException | ServletException e) {
      log.error("Error filtering path" + path + ", " + e.getMessage());
      new ErrorRedirect().sendRedirect(Path.MAIN_MENU, (HttpServletResponse) response);
    }
  }

  private boolean isAccess(HttpSession httpSession, boolean activeRole, boolean access,
      String path) {
    if (activeRole) {
      Role currentRole = (Role) httpSession.getAttribute("roles");
      access = checkPathByRole(currentRole, path);
    }
    return access;
  }

  private boolean checkPath(String path) {
    return GuardPath.GUARDS_ADMIN.contains(path)
        || GuardPath.GUARDS_LIBRARIAN.contains(path)
        || GuardPath.GUARDS_PATHS_READER.contains(path);
  }

  private boolean checkPathByRole(Role currentRole, String path) {
    if (currentRole.getName() == RoleName.ADMIN_ROLE) {
      return GuardPath.GUARDS_ADMIN.contains(path) || GuardPath.GUARDS_ALL.contains(path);
    } else if (currentRole.getName() == RoleName.LIBRARIAN_ROLE) {
      return GuardPath.GUARDS_LIBRARIAN.contains(path) || GuardPath.GUARDS_ALL.contains(path);
    } else if (currentRole.getName() == RoleName.READER_ROLE) {
      return GuardPath.GUARDS_PATHS_READER.contains(path) || GuardPath.GUARDS_ALL.contains(path);
    }
    return false;
  }

  private void redirectFilter(LogginParam logginParam, ServletRequest request,
      ServletResponse response, FilterChain chain, HttpServletResponse servletResponse)
      throws IOException, ServletException {
    if (logginParam.isLoggedIn() && logginParam.isGuard() && logginParam.isAccess()) {
      chain.doFilter(request, response);
    } else if (!logginParam.isLoggedIn() && logginParam.isGuard()) {
      servletResponse.sendRedirect(Path.SIGN_IN_PAGE);
    } else if (isAuthAccess(logginParam.isLoggedIn(), logginParam.isLoginRequest(),
        logginParam.isSingUpRequest(), logginParam.isGuard())) {
      servletResponse.sendRedirect(Path.MAIN_MENU);
    } else {
      chain.doFilter(request, response);
    }
  }

  private boolean isAuthAccess(boolean loggedIn, boolean loginRequest, boolean singUpRequest,
      boolean isGuard) {
    return (loggedIn && (loginRequest || singUpRequest)) || (loggedIn && isGuard);
  }
}
