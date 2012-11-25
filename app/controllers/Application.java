package controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import play.*;
import play.mvc.*;
import play.data.*;

import models.*;
import views.html.*;
import java.util.*;
import javax.persistence.*;
import com.octo.captcha.service.image.*;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.util.Locale;
import java.io.IOException;

public class Application extends Controller {
  static Form<CommentForm> commentForm = form(CommentForm.class);
  
  public static Result index() {
    Post frontPost = Post.find.orderBy("postedAt").findList().get(0);
    List<Post> olderPosts = Post.find.orderBy("postedAt").findList().subList(1, 3);
    return ok(index.render(
    frontPost, olderPosts
    ));
  }
  public static Result show(Long id) {
    Post post = Post.find.byId(id);
    String randomID = UUID.randomUUID().toString();
    return ok(show.render(post, commentForm, randomID));
  }

  public static Result captcha(String randomID) {
    try{
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageCaptchaService service = Captcha.getService();
    BufferedImage image = service.getImageChallengeForID(randomID);
    ImageIO.write(image, "jpg", baos);
    return ok(baos.toByteArray()).as("image/jpeg");
    } catch(IOException e) {
      System.out.printf("Captcha Creation failed\n");
      return notFound();
    }
  }

  public static Result postComment(Long postId, String randomID) {
    Form<CommentForm> filled = commentForm.bindFromRequest();
    Post post = Post.find.byId(postId);

    if(filled.hasErrors()) { 
      return badRequest(show.render(post, commentForm, randomID));
    }
    else {
      CommentForm comment = filled.get();
      post.addComment(comment.fullname, comment.email, comment.content);
      session().clear();
      flash("success", "Thanks for posting");
      return show(postId); 
    }
  }
}

