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
				Map<String, List<Object>> all = (Map<String, List<Object>>)Yaml.load("initial-data.yml");
				Ebean.save(all.get("users"));
				Ebean.save(all.get("posts"));
				Ebean.save(all.get("comments"));
			}
		}
	}
}
