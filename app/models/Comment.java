package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
@Table(name="comments")
public class Comment extends Model {

  @Id
  public Long id;
  @ManyToOne
	public User author;
	public Date postedAt;

	@Lob	
  @Basic(fetch = FetchType.EAGER)
	public String content;

	@ManyToOne
	public Post post;

  public static Model.Finder<Long, Comment> find = new Model.Finder(Long.class, Comment.class);
	public Comment(Post post, User author, String content) {
		this.post = post;
		this.author = author;
		this.content = content;
    this.postedAt = new Date();
	}

  public static List<Comment> findByPost(Long postId) {
    return find.where().eq("post.id", postId).findList(); 
  }

}
