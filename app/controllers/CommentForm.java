package controllers;

import com.octo.captcha.service.image.ImageCaptchaService;

public class CommentForm {
  public String fullname;
  public String email;
  public String content;
  public String randomID;
  public String code;
  
  public String validate()  {
    ImageCaptchaService service = Captcha.getService ();

    if(!fullname.isEmpty() && !email.isEmpty() && !content.isEmpty()
        && service.validateResponseForID(randomID, code)) {
      return null;
    } else {
      return "failed";
    }
  }
}
