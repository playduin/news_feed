package playduin.newsfeed.models.news.network.second;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

public interface SecondSourceRetrofitApi {

    @GET("/r/news/.rss")
    @Streaming
    Call<ResponseBody> getCallXML();
}
