package su.levenetc.androidplayground.samples;

import su.levenetc.androidplayground.models.UserModel;

/**
 * Created by Eugene Levenetc on 21/03/2016.
 */
public class AutoValueSample {
	public static void run() {
		UserModel user = UserModel.builder().birthday("10-10-1999").name("Bob").type(10).build();
		String birthday = user.birthday();
		String name = user.name();
		int type = user.type();
	}
}
