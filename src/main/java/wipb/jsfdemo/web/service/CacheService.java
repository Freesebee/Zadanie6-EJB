package wipb.jsfdemo.web.service;

import wipb.jsfdemo.web.models.CachedPrimeNumber;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.interceptor.Interceptors;
import java.math.BigInteger;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Singleton
public class CacheService {

    HashMap<BigInteger, CachedPrimeNumber> _dictionary = new HashMap<>();

    @Resource
    private TimerService _timer;

    @PostConstruct
    private void init() {
        _timer.createTimer(1000, 2000, "IntervalTimerDemo_Info");
    }

    @Interceptors(Interceptor.class)
    public void CacheValue(BigInteger number, Boolean isPrimeNumber) {
        _dictionary.put(number, new CachedPrimeNumber(number, isPrimeNumber));
    }

    @Interceptors(Interceptor.class)
    public Optional<CachedPrimeNumber> CheckPrimeNumber(BigInteger number) {
        return Optional.ofNullable(_dictionary.getOrDefault(number, null));
    }

    @Schedule(minute = "*/2")
    @Interceptors(Interceptor.class)
    public void ClearCache() {
        List<BigInteger> numbersToRemove = new ArrayList<>();

        _dictionary.forEach((key, value) -> {
            long diffInMillis = Math.abs(Date.from(Instant.now()).getTime() - value.getCreatedDate().getTime());
            long diffInMinutes = TimeUnit.MINUTES.convert(diffInMillis, TimeUnit.MINUTES);

            if(diffInMinutes >= 2) {
                numbersToRemove.add(key);
            }
        });

        numbersToRemove.forEach(number -> _dictionary.remove(number));
    }
}
