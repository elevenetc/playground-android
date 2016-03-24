package com.squareup.picasso;

import su.levenetc.androidplayground.utils.PlayUtils;

/**
 * Created by Eugene Levenetc on 23/03/2016.
 */
public class PicassoInjector {
	public static void dispatcher(Picasso picasso) {

		DispatcherWrapper dispatcherWrapper = DispatcherWrapper.cloneInstance(picasso.dispatcher);

		try {
			PlayUtils.setFinalInstanceField(picasso, "dispatcher", dispatcherWrapper);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
