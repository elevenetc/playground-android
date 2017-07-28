package su.levenetc.androidplayground.activities;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import su.levenetc.androidplayground.persistence.AppDatabase;
import su.levenetc.androidplayground.persistence.User;
import su.levenetc.androidplayground.persistence.UserDao;

import java.util.List;

public class RoomActivity extends AppCompatActivity {
	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AppDatabase db = Room.databaseBuilder(
				getApplicationContext(),
				AppDatabase.class, "database-name"
		).build();
		final UserDao userDao = db.userDao();

		new Thread(() -> {
			final List<User> users = userDao.getAll();
			if (users.isEmpty()) {
				userDao.insertAll(new User(1, "hello"));
			}
		}).start();
	}

}
