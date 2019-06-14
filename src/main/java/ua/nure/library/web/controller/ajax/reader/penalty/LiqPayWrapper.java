package ua.nure.library.web.controller.ajax.reader.penalty;

import com.liqpay.LiqPay;
import java.net.Proxy;

/**
 * @author Artem Kudria
 */
public class LiqPayWrapper extends LiqPay {

  public LiqPayWrapper(String publicKey, String privateKey) {
    super(publicKey, privateKey);
  }

  public LiqPayWrapper(String publicKey, String privateKey, Proxy proxy, String proxyLogin,
      String proxyPassword) {
    super(publicKey, privateKey, proxy, proxyLogin, proxyPassword);
  }

  @Override
  public String str_to_sign(String str) {
    return super.str_to_sign(str);
  }
}
