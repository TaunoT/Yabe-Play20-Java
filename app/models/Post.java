package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
@Table(name = "posts")
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

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
  public List<Comment> comments = new ArrayList<Comment>();

  @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
  public Set<Tag> tags = new TreeSet<Tag>();

  public static Model.Finder<Long, Post> find = new Model.Finder(Long.class,Post.class);

  public Post addComment(String author_fullname, String author_email, String content) {
    User commenter = User.findByEmail(author_email);
    Comment newComment;
    if (commenter != null) {
      newComment = new Comment(this, commenter, content);
    } else {
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
    // this.comments = new ArrayList<Comment>();
    // this.tags = new TreeSet<Tag>();
    this.author = author;
    this.title = title;
    this.content = content;
    this.postedAt = new Date();
  }

  public static Post create(Post post, String email, String content) {
    post.author = User.find.where().eq("email", email).findUnique();
    post.content = content;
    post.save();
    return post;
  }

  public Post previous() {
    Post result = Post.find.where().lt("postedAt", postedAt)
        .orderBy("postedAt desc").setMaxRows(1).findUnique();
    if (result != null)
      return result;
    return null;
  }

  public Post next() {
    Post result = Post.find.where().gt("postedAt", postedAt)
        .orderBy("postedAt asc").setMaxRows(1).findUnique();
    if (result != null)
      return result;
    return null;
  }

  public Post tagItWith(String name) {
    tags.add(Tag.findOrCreateByName(name));
    this.saveManyToManyAssociations("tags");
    return this;
  }

  public static List<Post> findTaggedWith(String... tags) {
    // String sql =
    // "select distinct p from Post p join p.tags as t where t.name in (:tags) group by p.id, p.author, p.title, p.content, p.postedAt having count(t.id) = :size";
    return Post.find.where().in("tags.name", tags).findList();
    /*
     * RawSql rawsql = RawSqlBuilder.parse(sql).create();
     * com.avaje.ebean.Query<Post> sqlquery = Ebean.find(Post.class);
     * sqlquery.setRawSql(rawsql); return sqlquery.findList();
     */
  }
  
  public static List<Post> findByUser(String user) {
    return Post.find.where().eq("author_email", user).orderBy("postedAt desc").findList();
  }
}
