package playduin.newsfeed.models.news.network.second;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.InterruptedIOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import playduin.newsfeed.BuildConfig;
import playduin.newsfeed.models.news.NewsFeedRepo;
import playduin.newsfeed.models.news.NewsItem;
import playduin.newsfeed.models.news.network.second.schemas.SecondSourceFeed;
import playduin.newsfeed.models.news.network.second.schemas.SecondSourceNewsItem;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class SecondSourceNetworkImpl implements SecondSourceNetwork {

    private final SecondSourceRetrofitApi service;

    private final SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-d HH:mm:ss zzz", Locale.ROOT);

    public SecondSourceNetworkImpl() {
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BASIC : HttpLoggingInterceptor.Level.NONE);

        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.readTimeout(10, TimeUnit.SECONDS);
        httpClient.writeTimeout(10, TimeUnit.SECONDS);
        httpClient.connectTimeout(10, TimeUnit.SECONDS);

        final Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.createSynchronous())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(httpClient.build())
                .baseUrl("https://source1.local")
                .build();

        service = retrofit.create(SecondSourceRetrofitApi.class);
    }

    @Override
    public Single<List<NewsItem>> getNews() {
        return getFeed().map(secondSourceFeed -> {
            final List<NewsItem> news = new ArrayList<>();
            for (SecondSourceNewsItem secondSourceNewsItem : secondSourceFeed.items) {
                news.add(new NewsItem(parseDate(secondSourceNewsItem.getPublished().replace('T', ' ').replace("+00:00", " GMT")), secondSourceNewsItem.getTitle(), NewsFeedRepo.SECOND_SOURCE, secondSourceNewsItem.getLink(), false));
            }
            return news;
        });
    }

    private Single<SecondSourceFeed> getFeed() {
        return Single.fromCallable(() -> {
            final Call<ResponseBody> call = service.getCallXML();
            try {
                final String data = Objects.requireNonNull(call.execute().body()).string();
                final String xml = data.substring(0, data.indexOf(">") + 1) + "<rss>" + data.substring(data.indexOf(">") + 1) + "</rss>";
                final Serializer serializer = new Persister();
                return serializer.read(SecondSourceFeed.class, xml);
            } catch (InterruptedIOException e) {
                final Serializer serializer = new Persister();
                return serializer.read(SecondSourceFeed.class, "<rss></rss>");
            }
        });
    }

    private long parseDate(String dateStr) {
        try {
            final Date date = parser.parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }
}
