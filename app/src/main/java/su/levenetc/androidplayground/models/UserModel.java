package su.levenetc.androidplayground.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

/**
 * Created by Eugene Levenetc on 19/03/2016.
 */
@AutoValue
public abstract class UserModel {

	@NonNull public abstract String name();

	@Nullable public abstract String birthday();

	public abstract int type();

	@AutoValue.Builder
	public abstract static class Builder {
		public abstract Builder name(String value);

		public abstract Builder birthday(String value);

		public abstract Builder type(int value);

		public abstract UserModel build();
	}

	public static Builder builder() {
		return new AutoValue_UserModel.Builder();
	}
}
