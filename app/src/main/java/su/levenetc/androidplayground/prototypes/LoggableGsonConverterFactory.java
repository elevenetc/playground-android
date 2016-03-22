package su.levenetc.androidplayground.prototypes;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;
import su.levenetc.androidplayground.utils.Utils;

/**
 * Copy of {@link retrofit2.converter.gson.GsonConverterFactory}
 */
public class LoggableGsonConverterFactory extends Converter.Factory {
	/**
	 * Create an instance using a default {@link Gson} instance for conversion. Encoding to JSON and
	 * decoding from JSON (when no charset is specified by a header) will use UTF-8.
	 */
	public static LoggableGsonConverterFactory create() {
		return create(new Gson());
	}

	/**
	 * Create an instance using {@code gson} for conversion. Encoding to JSON and
	 * decoding from JSON (when no charset is specified by a header) will use UTF-8.
	 */
	public static LoggableGsonConverterFactory create(Gson gson) {
		return new LoggableGsonConverterFactory(gson);
	}

	private final Gson gson;

	private LoggableGsonConverterFactory(Gson gson) {
		if (gson == null) throw new NullPointerException("gson == null");
		this.gson = gson;
	}

	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
		TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
		return new GsonResponseBodyConverter<>(gson, adapter);
	}

	@Override
	public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
		TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
		return new GsonRequestBodyConverter<>(gson, adapter);
	}

	static final class GsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
		private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
		private static final Charset UTF_8 = Charset.forName("UTF-8");

		private final Gson gson;
		private final TypeAdapter<T> adapter;

		GsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
			this.gson = gson;
			this.adapter = adapter;
		}

		@Override public RequestBody convert(T value) throws IOException {
			Utils.startTime("reqTime");
			Buffer buffer = new Buffer();
			Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
			JsonWriter jsonWriter = gson.newJsonWriter(writer);
			adapter.write(jsonWriter, value);
			jsonWriter.close();
			RequestBody result = RequestBody.create(MEDIA_TYPE, buffer.readByteString());
			Log.i("reqTime", "request parse finish in:" + Utils.endTime("reqTime"));
			return result;
		}
	}

	static final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
		private final Gson gson;
		private final TypeAdapter<T> adapter;

		GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
			this.gson = gson;
			this.adapter = adapter;
		}

		@Override public T convert(ResponseBody value) throws IOException {
			Utils.startTime("respTime");
			JsonReader jsonReader = gson.newJsonReader(value.charStream());
			T result;
			try {
				result = adapter.read(jsonReader);
			} finally {
				value.close();
			}
			Log.i("respTime", "response parse finish in:" + Utils.endTime("respTime"));
			return result;
		}
	}

}