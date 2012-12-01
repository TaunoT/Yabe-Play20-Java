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
  
  @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  public Set<Tag> tags = new TreeSet<Tag>();
  //public List<Tag> tags = new ArrayList<Tag>();

  public static Model.Finder<Long, Post> find = new Model.Finder(Long.class, Post.class);

	public Post addComment(String author_fullname, String author_email, String content) {
    User commenter = User.findByEmail(author_email);
    Comment newComment;
    if(commenter != null){
      newComment = new Comment(this, commenter, content);
    }
    else {
      commenter = new User(author_email, "", author_fullname);
      commenter.save();
      newComment = new Comment(this, commenter, content);
    }
		newComment.save();
		this.comments.add(newComment);
		this.save();
		return this;
	}

  public Post(String title, User author, String content) {
    this.comments = new ArrayList<Comment>();
    this.tags = new TreeSet<Tag>();
    //this.tags = new ArrayList<Tag>();
    this.author = author;
    this.title = title;
    this.content = content;
    this.postedAt = new Date();
  }
  
  public static Post create(Post post, String email, String content)
  {
    post.author = User.find.where().eq("email", email).findUnique();
    post.content = content;
    post.save();
    return post;
  }

  public Post previous() {
    List<Post> result = Post.find.orderBy("postedAt desc").where().lt("postedAt", postedAt).findList();
    if(result.size()>0)
      return result.get(0);
    return null;
  }
  public Post next() {
    List<Post> result = Post.find.orderBy("postedAt asc").where().gt("postedAt", postedAt).findList();
    if(result.size()>0)
      return result.get(0);
    return null;
  }
  
  public Post tagItWith(String name) {
    tags.add(Tag.findOrCreateByName(name));
    this.saveManyToManyAssociations("tags");
    return this;
  }
    
  public static List<Post> findTaggedWith(String... tags) {
    String sql = "select distinct p from Post p join p.tags as t where t.name in (:tags) group by p.id, p.author, p.title, p.content, p.postedAt having count(t.id) = :size";
   com.avaje.ebean.Query<Post> sqlquery = Ebean.createQuery(Post.class, sql);
    sqlquery.setParameter("tags", tags);
    sqlquery.setParameter("size", tags.length);
    return sqlquery.findList();
  }
}
