package playduin.newsfeed.models.news.network.first;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import playduin.newsfeed.BuildConfig;
import playduin.newsfeed.models.news.NewsFeedRepo;
import playduin.newsfeed.models.news.NewsItem;
import playduin.newsfeed.models.news.network.first.schemas.FirstSourceFeed;
import playduin.newsfeed.models.news.network.first.schemas.FirstSourceNewsItem;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class FirstSourceNetworkImpl implements FirstSourceNetwork {

    private final FirstSourceRetrofitApi service;

    private final SimpleDateFormat parser = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss zzz", Locale.ROOT);

    public FirstSourceNetworkImpl() {
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

        service = retrofit.create(FirstSourceRetrofitApi.class);
    }

    @Override
    public Single<List<NewsItem>> getNews() {
        return getFeed().map(firstSourceFeed -> {
            final List<NewsItem> news = new ArrayList<>();
            for (FirstSourceNewsItem firstSourceNewsItem : firstSourceFeed.items) {
                news.add(new NewsItem(parseDate(firstSourceNewsItem.getPubDate()), firstSourceNewsItem.getTitle(), NewsFeedRepo.FIRST_SOURCE, firstSourceNewsItem.getLink(), false));
            }
            return news;
        });
    }

    private Single<FirstSourceFeed> getFeed() {
        return service.getNewsList();
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
