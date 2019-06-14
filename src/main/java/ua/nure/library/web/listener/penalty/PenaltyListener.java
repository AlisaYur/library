package ua.nure.library.web.listener.penalty;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;
import ua.nure.library.web.listener.verifttoken.CheckAllVerifyTokensOnExpiry;

@WebListener
public class PenaltyListener implements ServletContextListener,
    HttpSessionListener, HttpSessionAttributeListener {

  private ScheduledExecutorService scheduledExecutorService;

  // -------------------------------------------------------
  // ServletContextListener implementation
  // -------------------------------------------------------
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    scheduledExecutorService.scheduleAtFixedRate(
        new CheckAllOrdersOnPenalty(), 0, 1, TimeUnit.DAYS);
    scheduledExecutorService.scheduleAtFixedRate(
        new CheckAllVerifyTokensOnExpiry(), 0, 1, TimeUnit.DAYS);
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    scheduledExecutorService.shutdownNow();
  }
}
