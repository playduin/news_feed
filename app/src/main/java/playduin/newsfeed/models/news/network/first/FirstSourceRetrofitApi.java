package playduin.newsfeed.models.news.network.first;

import io.reactivex.rxjava3.core.Single;
import playduin.newsfeed.models.news.network.first.schemas.FirstSourceFeed;
import retrofit2.http.GET;

public interface FirstSourceRetrofitApi {

    @GET("/rss/news/?limit=30")
    Single<FirstSourceFeed> getNewsList();
}
