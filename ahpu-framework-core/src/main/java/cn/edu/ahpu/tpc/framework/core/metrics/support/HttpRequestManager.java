package cn.edu.ahpu.tpc.framework.core.metrics.support;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class HttpRequestManager {
	
	private final AtomicLong idSeed = new AtomicLong(1000);
	
	private final static HttpRequestManager instance = new HttpRequestManager();
	
	private final ConcurrentMap<String, HttpRequestStat>  httpRequests   = new ConcurrentHashMap<String, HttpRequestStat>();
	
	private HttpRequestManager(){

    }
	
	public long generateSqlId() {
        return idSeed.incrementAndGet();
    }

    public static final HttpRequestManager getInstance() {
        return instance;
    }
    
    public ConcurrentMap<String, HttpRequestStat> getHttpRequests() {
        return httpRequests;
    }
}
