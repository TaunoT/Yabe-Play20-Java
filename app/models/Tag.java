package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class Tag extends Model implements Comparable<Tag> {

  @Id
  public Long Id;
  public String name;

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
    Tag tag = Tag.find.where("name", name).findUnique();
    if(tag == null) {
      tag = new Tag(name);
    }
    return tag;
  }
}
