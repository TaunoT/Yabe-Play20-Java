package controllers;


public class CommentForm {
  public String fullname;
  public String email;
  public String content;
  public String randomID;
  public String code;
  
  public String validate()  {
    if(!fullname.isEmpty() && !email.isEmpty() && !content.isEmpty())
      return null;
    else
      return "failed";
  }
}
