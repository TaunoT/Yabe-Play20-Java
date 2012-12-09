package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
@Table(name="tags")
public class Tag extends Model implements Comparable<Tag> {

  @Id
  public Long id;
  
  @Basic(fetch = FetchType.EAGER)
  public String name;

/*
  @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
  public Set<Post> posts = new TreeSet<Post>();
*/

  public static Model.Finder<Long, Tag> find = new Model.Finder(Long.class, Tag.class);

  public static List<Tag> findAll() {
    return find.all();
  }
  
  private Tag(String name) {
    this.name = name;
  }
  
  public String toString() {
    return name;
  }
  
  public int compareTo(Tag otherTag) {
    return name.compareTo(otherTag.name);
  }

  public static Tag findOrCreateByName(String name) {
    Tag tag = Tag.find.where().eq("name", name).findUnique();
    if(tag == null) {
      tag = new Tag(name);
    }
    return tag;
  }
  
  public static List<Map> getCloud() {
    String sql = "select new map(t.name as tag, count(p.id) as pound) from Post p join p.tags as t group by t.name order by t.name";
    com.avaje.ebean.Query<Map> sqlquery = Ebean.createQuery(Map.class, sql);
    return sqlquery.findList();
  }
}
