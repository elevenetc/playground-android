package com.squareup.picasso;

import su.levenetc.androidplayground.models.TimeSession;
import su.levenetc.androidplayground.utils.PlayUtils;

/**
 * Created by Eugene Levenetc on 23/03/2016.
 */
public class InjectUtils {
	public static void injectPicasso(Picasso picasso, TimeSession session) {

		DispatcherWrapper dispatcherWrapper = DispatcherWrapper.cloneInstance(picasso.dispatcher, session);

		try {
			PlayUtils.setFinalInstanceField(picasso, "dispatcher", dispatcherWrapper);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
