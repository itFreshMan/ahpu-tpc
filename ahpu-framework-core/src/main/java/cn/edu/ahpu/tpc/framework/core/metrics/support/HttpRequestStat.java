package cn.edu.ahpu.tpc.framework.core.metrics.support;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class HttpRequestStat {
	private final String     uri;
	private long             id;
    private final AtomicLong executeErrorCount     = new AtomicLong();
    private Throwable        executeErrorLast;
    private long             executeErrorLastTime;
    
    //执行成功次数
    private final AtomicLong executeSuccessCount = new AtomicLong();
    //最慢消耗时间（纳秒）
    private final AtomicLong executeSpanNanoMax    = new AtomicLong();
    //最慢发生的时间（毫秒）
    private long executeSpanMaxOccurTime;
    //总共时间（纳秒）
    private final AtomicLong executeSpanNanoTotal = new AtomicLong();
    //最后一次执行的时间
    private long executeLastStartTime;
    
    public HttpRequestStat(String uri){
        this.uri = uri;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getUri() {
        return uri;
    }
    
    public Date getExecuteLastStartTime() {
        if (executeLastStartTime <= 0) {
            return null;
        }

        return new Date(executeLastStartTime);
    }
    
    public void setExecuteLastStartTime(long executeLastStartTime) {
        this.executeLastStartTime = executeLastStartTime;
    }
    
    public Date getExecuteSpanMaxOccurTime() {
        if (executeSpanMaxOccurTime <= 0) {
            return null;
        }
        return new Date(executeSpanMaxOccurTime);
    }
    
    public Date getExecuteErrorLastTime() {
        if (executeErrorLastTime <= 0) {
            return null;
        }
        return new Date(executeErrorLastTime);
    }
    
    public void incrementExecuteSuccessCount() {
        executeSuccessCount.incrementAndGet();
    }

    public long getExecuteSuccessCount() {
        return executeSuccessCount.get();
    }
    
    public long getExecuteMillisTotal() {
        return executeSpanNanoTotal.get() / (1000 * 1000);
    }

    public long getExecuteMillisMax() {
        return executeSpanNanoMax.get() / (1000 * 1000);
    }

    public long getErrorCount() {
        return executeErrorCount.get();
    }
    
    public long getExecuteCount() {
        return getErrorCount() + getExecuteSuccessCount();
    }
    
    public Throwable getExecuteErrorLast() {
        return executeErrorLast;
    }

    public void error(Throwable error) {
        executeErrorCount.incrementAndGet();
        executeErrorLastTime = System.currentTimeMillis();
        executeErrorLast = error;

    }
    
    public void addExecuteTime(long nanoSpan) {
        executeSpanNanoTotal.addAndGet(nanoSpan);

        for (;;) {
            long current = executeSpanNanoMax.get();
            if (current < nanoSpan) {
                if (executeSpanNanoMax.compareAndSet(current, nanoSpan)) {
                    // 可能不准确，但是绝大多数情况下都会正确，性能换取一致性
                	executeSpanMaxOccurTime = System.currentTimeMillis();

                    break;
                } else {
                    continue;
                }
            } else {
                break;
            }
        }
    }
}
