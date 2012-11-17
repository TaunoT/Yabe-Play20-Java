package controllers;

import java.util.*;
import javax.persistence.*;
import play.*;
import play.data.*;
import play.mvc.*;

import views.html.*;
import models.*;

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
    return ok(show.render(post, commentForm));
  }
  public static Result postComment(Long postId) {
    Form<CommentForm> filled = commentForm.bindFromRequest();
    Post post = Post.find.byId(postId);

    if(filled.hasErrors()) { 
      return badRequest(show.render(post, commentForm));
    }
    else {
      CommentForm comment = filled.get();
      post.addComment(comment.fullname, comment.email, comment.content);
      return show(postId); 
    }
  }
}

