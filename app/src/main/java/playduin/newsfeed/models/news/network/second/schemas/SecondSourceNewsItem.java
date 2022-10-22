package playduin.newsfeed.models.news.network.second.schemas;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "entry", strict = false)
public class SecondSourceNewsItem {

    @Element(name = "title")
    private String title;

    @Path("link")
    @Attribute(name = "href")
    private String link;

    @Element(name = "published")
    private String published;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getPublished() {
        return published;
    }
}
