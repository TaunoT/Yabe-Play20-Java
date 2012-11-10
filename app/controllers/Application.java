package controllers;

import java.util.*;
import javax.persistence.*;
import play.*;
import play.mvc.*;

import views.html.*;
import models.*;

public class Application extends Controller {
  
  public static Result index() {
    Post frontPost = Post.find.orderBy("postedAt").findList().get(0);
    List<Post> olderPosts = Post.find.orderBy("postedAt").findList().subList(1, 3);
    return ok(index.render(
//    Post.find.all(), User.find.all()
    frontPost, olderPosts
    ));
  }
  
}
