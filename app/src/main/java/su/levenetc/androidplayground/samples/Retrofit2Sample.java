package su.levenetc.androidplayground.samples;

import android.support.annotation.NonNull;
import android.util.Log;

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
import su.levenetc.androidplayground.prototypes.LoggableGsonConverterFactory;
import su.levenetc.androidplayground.utils.Utils;

/**
 * Created by Eugene Levenetc on 21/03/2016.
 */
public class Retrofit2Sample {

	private static GPlusService service;

	public static void run() {

		createGPlusService();

		new Thread(new Runnable() {
			@Override public void run() {

				Utils.startTime("externalTime");

				String gPlusApiKey = BuildConfig.GOOGLE_PLUS_API_KEY;
				String googlePlusUserId = BuildConfig.GOOGLE_PLUS_USER_ID;
				Call<Profile> objectCall = service.getProfile(googlePlusUserId, gPlusApiKey);
				Response<Profile> response = null;
				try {
					response = objectCall.execute();
					Log.i("logReq", "externalTime:" + Utils.endTime("externalTime") + "ms");
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (response != null) {
					Profile body = response.body();
					if (body != null && body.error != null) {

					}
				}
			}
		}).start();
	}

	private static void createGPlusService() {
		if (service != null) return;
		OkHttpClient client = buildClient();

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://www.googleapis.com/plus/v1/")
				.addConverterFactory(getConverterFactory())
				.client(client)
				.build();

		service = retrofit.create(GPlusService.class);
	}

	@NonNull private static Converter.Factory getConverterFactory() {
		return LoggableGsonConverterFactory.create();
	}

	@NonNull private static OkHttpClient buildClient() {

		OkHttpClient.Builder builder = new OkHttpClient.Builder();

		builder.addInterceptor(new Interceptor() {
			@Override public okhttp3.Response intercept(Chain chain) throws IOException {
				Utils.startTime("internalTime");
				Log.i("logReq", "start");
				Request request = chain.request();

				okhttp3.Response response = chain.proceed(request);

				Log.i("logReq", "end with code:" + response.code() + " with total time:" + Utils.endTime("internalTime") + "ms");

				return response;
			}
		});

		return builder.build();
	}

	public interface GPlusService {
		@GET("people/{user}") Call<Profile> getProfile(@Path("user") String user, @Query("key") String key);
	}

	private static class Profile {
		GPlusError error;
		int circledByCount;
	}

	private static class GPlusError {
		int code;
		String message;
	}
}
