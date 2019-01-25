package com.homepaas.sls.domain;

import javax.inject.Named;

/**
 * Created by CJJ on 2016/9/13.
 * an empty annotation just for quick access concurrent  processor-type class
 * such as jobExecutor which always needs "@Name('concurrent')" annotation to be initialized
 */
@Named("concurrent")
public @interface Concurrent {
}
