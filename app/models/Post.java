package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
@Table(name="posts")
public class Post extends Model {
	
	@Id
	public Long id;

	public String title;
	public Date postedAt;

	@Lob
  @Basic(fetch = FetchType.EAGER)
	public String content;
	
	@ManyToOne
	public User author;
	
	@OneToMany(mappedBy="post", cascade=CascadeType.ALL)
	public List<Comment> comments = new ArrayList<Comment>();

  public static Model.Finder<Long, Post> find = new Model.Finder(Long.class, Post.class);

	public Post addComment(User author, String content) {
		Comment newComment = new Comment(this, author, content);
		newComment.save();
		this.comments.add(newComment);
		this.save();
		return this;
	}

  public Post(String title, User author, String content) {
    this.author = author;
    this.title = title;
    this.content = content;
  }
  
  public static Post create(Post post, String email, String content)
  {
    post.author = User.find.where().eq("email", email).findUnique();
    post.content = content;
    post.save();
    return post;
  }

 



}
