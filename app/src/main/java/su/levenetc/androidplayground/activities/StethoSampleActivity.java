package su.levenetc.androidplayground.activities;

import android.app.Activity;
import android.os.Bundle;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Eugene Levenetc on 17/07/2016.
 */
public class StethoSampleActivity extends Activity {
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new StethoInterceptor()).build();

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://api.github.com/elevenetc/")
				.addConverterFactory(GsonConverterFactory.create())
				.client(client)
				.build();

		GithubApi githubApi = retrofit.create(GithubApi.class);

		new Thread(new Runnable() {
			@Override public void run() {
				try {
					Response<Resp> resp = githubApi.loadIssues().execute();
					if (resp != null) {

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public interface GithubApi {
		@GET("repos") Call<Resp> loadIssues();
	}

	public static class Resp {
		public String message;
	}
}
