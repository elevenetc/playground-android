package su.levenetc.androidplayground.samples;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import su.levenetc.androidplayground.BuildConfig;

/**
 * Created by Eugene Levenetc on 21/03/2016.
 */
public class Retrofit2Sample {
	public static void run() {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://www.googleapis.com/plus/v1/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		final GPlusService service = retrofit.create(GPlusService.class);

		new Thread(new Runnable() {
			@Override public void run() {
				String gPlusApiKey = BuildConfig.GOOGLE_PLUS_API_KEY;
				Call<Profile> objectCall = service.getProfile("113181101324425321108", gPlusApiKey);
				Response<Profile> response = null;
				try {
					response = objectCall.execute();
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
