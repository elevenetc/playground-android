package su.levenetc.androidplayground.samples;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestHandler;
import com.squareup.picasso.Target;
import com.squareup.picasso.UrlConnectionDownloader;
import com.squareup.picasso.PicassoInjector;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import su.levenetc.androidplayground.BuildConfig;
import su.levenetc.androidplayground.models.GPlusProfile;
import su.levenetc.androidplayground.prototypes.LoggableGsonConverterFactory;
import su.levenetc.androidplayground.utils.PlayUtils;

/**
 * Created by Eugene Levenetc on 21/03/2016.
 */
public class Retrofit2Sample {

	private static GPlusService service;
	private static Picasso picasso;
	private static OkHttpClient retrofitClient;

	public static void run(Context context) {

		initRetrofitClient();
		initGPlusService();
		initPicasso(context);

		new Thread(new Runnable() {
			@Override public void run() {

				PlayUtils.startTime("externalTime");

				String gPlusApiKey = BuildConfig.GOOGLE_PLUS_API_KEY;
				String googlePlusUserId = BuildConfig.GOOGLE_PLUS_USER_ID;
				Call<GPlusProfile> objectCall = service.getProfile(googlePlusUserId, gPlusApiKey);
				Response<GPlusProfile> response = null;
				try {
					response = objectCall.execute();
					Log.i("logReq", "externalTime:" + PlayUtils.endTime("externalTime") + "ms");
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (response != null) {
					GPlusProfile body = response.body();
					if (body != null && body.error != null) {

					}
				}
			}
		}).start();

		loadImage();
	}

	private static void initPicasso(Context context) {
		if (picasso != null) return;

		Picasso.Builder builder = new Picasso.Builder(context);
		builder.loggingEnabled(true);
		builder.downloader(new UrlConnectionDownloader(context));

//		OkHttpDownloader downloader = new OkHttpDownloader(context);

		builder.addRequestHandler(new RequestHandler() {
			@Override public boolean canHandleRequest(com.squareup.picasso.Request data) {
				return false;
			}

			@Override public Result load(com.squareup.picasso.Request request, int networkPolicy) throws IOException {
				return null;
			}
		});
		builder.requestTransformer(new Picasso.RequestTransformer() {
			@Override public com.squareup.picasso.Request transformRequest(com.squareup.picasso.Request request) {
				PlayUtils.startTime("imageLoading");
				return request;
			}
		});

//		builder.downloader(downloader);
		picasso = builder.build();
		PicassoInjector.dispatcher(picasso);
	}

	private static void loadImage() {
		picasso.load("https://www.newton.ac.uk/files/covers/968361.jpg")
				.into(new Target() {
					      @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

					      }

					      @Override public void onBitmapFailed(Drawable errorDrawable) {

					      }

					      @Override public void onPrepareLoad(Drawable placeHolderDrawable) {

					      }
				      }
				);
	}

	private static void initGPlusService() {
		if (service != null) return;

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://www.googleapis.com/plus/v1/")
				.addConverterFactory(getConverterFactory())
				.client(retrofitClient)
				.build();

		service = retrofit.create(GPlusService.class);
	}

	@NonNull private static Converter.Factory getConverterFactory() {
		return LoggableGsonConverterFactory.create();
	}

	private static void initRetrofitClient() {

		OkHttpClient.Builder builder = new OkHttpClient.Builder();


		builder.addInterceptor(new Interceptor() {
			@Override public okhttp3.Response intercept(Chain chain) throws IOException {
				PlayUtils.startTime("internalTime");
				Request request = chain.request();
				Log.i("logReq", "start:" + request.url());

				okhttp3.Response response = chain.proceed(request);

				Log.i("logReq", "end with code:" + response.code() + " with total time:" + PlayUtils.endTime("internalTime") + "ms");

				return response;
			}
		});

		retrofitClient = builder.build();
	}

	public interface GPlusService {
		@GET("people/{user}") Call<GPlusProfile> getProfile(@Path("user") String user, @Query("key") String key);

		@GET("people/{user}") Call<GPlusProfile> activities(@Path("user") String user, @Query("key") String key);
	}

}
