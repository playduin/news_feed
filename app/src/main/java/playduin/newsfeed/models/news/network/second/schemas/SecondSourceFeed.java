package playduin.newsfeed.models.news.network.second.schemas;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "feed", strict = false)
public class SecondSourceFeed {

    @Path("feed")
    @ElementList(name = "entry", inline = true, required = false)
    public List<SecondSourceNewsItem> items;
}
