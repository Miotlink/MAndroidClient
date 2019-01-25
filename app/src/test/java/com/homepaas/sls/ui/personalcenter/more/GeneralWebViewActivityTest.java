package com.homepaas.sls.ui.personalcenter.more;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

/**
 * on 2016/4/6 0006
 *
 * @author zhudongjie .
 */
@RunWith(RobolectricTestRunner.class)
public class GeneralWebViewActivityTest {

    @Before
    public void setUp() {

    }

    @Test
    public void testGeneralWebView() {
        GeneralWebViewActivity activity = Robolectric.buildActivity(GeneralWebViewActivity.class)
                .create()
                .resume()
                .get();

        Assert.assertNotNull(activity);
    }
}