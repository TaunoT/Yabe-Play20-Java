package controllers;

import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

public class Captcha {
  private static ImageCaptchaService service = 
    new DefaultManageableImageCaptchaService();
  public static ImageCaptchaService getService() {
    return service;
  }
}

