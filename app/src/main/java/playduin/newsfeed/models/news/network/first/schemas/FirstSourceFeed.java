package playduin.newsfeed.models.news.network.first.schemas;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "channel", strict = false)
public class FirstSourceFeed {

    @Path("channel")
    @ElementList(name = "item", inline = true, required = false)
    public List<FirstSourceNewsItem> items;
}
