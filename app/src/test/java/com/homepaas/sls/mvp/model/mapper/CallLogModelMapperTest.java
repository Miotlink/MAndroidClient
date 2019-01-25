package com.homepaas.sls.mvp.model.mapper;

import com.homepaas.sls.domain.entity.CallLog;
import com.homepaas.sls.mvp.model.CallLogModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * on 2016/4/6 0006
 *
 * @author zhudongjie .
 */
public class CallLogModelMapperTest {

    private List<CallLog> callLogs;

    @Before
    public void setUp() throws Exception {
        callLogs = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            CallLog callLog = new CallLog();
            callLog.setPhoneNumber(String.valueOf(i%6));
            callLog.setName(String.valueOf(i%6)+i);
            callLog.setTime(System.currentTimeMillis()+i+"");
            callLogs.add(callLog);
        }
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void callLogSortTest(){
        CallLogModelMapper mapper = new CallLogModelMapper();
        List<CallLogModel> modelList = mapper.transform(callLogs);
        assertEquals(6,modelList.size());
        System.out.println(modelList);
    }
}