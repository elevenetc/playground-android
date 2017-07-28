package su.levenetc.androidplayground.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {
	@Query("SELECT * FROM user")
	List<User> getAll();

	@Query("SELECT * FROM user WHERE id IN (:userIds)")
	List<User> loadAllByIds(int[] userIds);

	@Query("SELECT * FROM user WHERE name LIKE :first LIMIT 1")
	User findByName(String first);

	@Insert
	void insertAll(User... users);

	@Delete
	void delete(User user);
}
