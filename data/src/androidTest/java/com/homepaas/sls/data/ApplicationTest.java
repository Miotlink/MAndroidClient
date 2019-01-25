package com.homepaas.sls.data;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.homepaas.sls.data.db.DBHelper;
import com.homepaas.sls.data.entity.App;
import com.homepaas.sls.data.entity.User;
import com.j256.ormlite.dao.Dao;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    private static final String TAG = "ApplicationTest";

    public ApplicationTest() {
        super(Application.class);
    }


    public void testDao() throws Exception {
        final int expected = 1;
        final int reality = 1;
        assertEquals(expected, reality);
    }

    private User createUser() {
        User user = new User();
        user.id = "12";
        user.name = "tk";
        Collection<App> apps = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            App app = new App();
            app.name = "hah" + i;
            app.user = user;
            apps.add(app);
        }
        user.apps = apps;

        return user;
    }

    //failed
    public void testSave() throws Exception {
        DBHelper dbHelper = new DBHelper(getContext());
        Dao<User, String> dao = dbHelper.getDao(User.class);
        dao.create(createUser());

    }


    public void testPartSave() throws Exception {
        User user = new User();
        user.id = "13";
        user.name = "神";
        App app1 = new App();
        app1.name = "应用1";
        app1.user = user;

        App app2 = new App();
        app2.name = "应用2";
        app2.user = user;
        DBHelper dbHelper = new DBHelper(getContext());
        Dao<User, String> dao = dbHelper.getDao(User.class);
        int col = dao.create(user);
        assertEquals(1, col);
        Dao<App, ?> appDao = dbHelper.getDao(App.class);
        col = appDao.create(app1);
        assertEquals(1, col);
        col = appDao.create(app2);
        assertEquals(1, col);
//
    }

    public void testGet() throws Exception {
        DBHelper dbHelper = new DBHelper(getContext());
        Dao<User, String> dao = dbHelper.getDao(User.class);

        User user = dao.queryForId("13");
        assertNotNull(user);
        assertEquals(2, user.apps.size());

    }
}