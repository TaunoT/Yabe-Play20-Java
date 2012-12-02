import play.*;
import play.libs.*;
import com.avaje.ebean.Ebean;
import models.*;
import java.util.*;

public class Global extends GlobalSettings {
	@Override
	public void onStart(Application app) {
		InitialData.insert(app);
	}

	static class InitialData {
		public static void insert(Application app) {
			if(Ebean.find(User.class).findRowCount() == 0) {
        Logger.debug("Read the initial-data.yml file.");

				Map<String, List<Object>> all = (Map<String, List<Object>>)Yaml.load("initial-data.yml");

        if(Ebean.find(User.class).findRowCount() == 0) {
          Logger.debug("Loading users from initial-data.yml.");
  				Ebean.save(all.get("users"));
        }

        if(Ebean.find(Tag.class).findRowCount() == 0) {
          Logger.debug("Loading tags from initial-data.yml.");
  				Ebean.save(all.get("tags"));
        }

        if(Ebean.find(Post.class).findRowCount() == 0) {
          Logger.debug("Loading posts from initial-data.yml.");
				  Ebean.save(all.get("posts"));
          
          for(Object post: all.get("posts")) {
            Logger.debug("Inserting posts-tags from initial-data.yml.");
            Ebean.saveManyToManyAssociations(post, "tags");
          }
        }


        if(Ebean.find(Comment.class).findRowCount() == 0) {
          Logger.debug("Loading comments from initial-data.yml.");
				  Ebean.save(all.get("comments"));
        }
			}
		}
	}
}
