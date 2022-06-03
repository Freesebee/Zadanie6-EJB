package wipb.jsfdemo.web.service;

import wipb.jsfdemo.web.models.CachedPrimeNumber;

import javax.ejb.*;
import javax.interceptor.Interceptors;
import java.math.BigInteger;
import java.util.Optional;

@Stateless
@Interceptors(Interceptor.class)
public class PrimeNumberService {

    @EJB
    private CacheService _cache;

    @Interceptors(Interceptor.class)
    public boolean primeCheck(BigInteger number) {

        Optional<CachedPrimeNumber> cachedPrimeNumber = _cache.CheckPrimeNumber(number);
        if(cachedPrimeNumber.isPresent()) {
            return cachedPrimeNumber.get().getPrime();
        }

        if (!number.isProbablePrime(5)) {
            _cache.CacheValue(number, false);
            return false;
        }

        BigInteger two = new BigInteger("2");
        if (two.equals(number) || BigInteger.ZERO.equals(number.mod(two))) {
            _cache.CacheValue(number, false);
            return false;
        }

        for (BigInteger i = new BigInteger("3"); i.multiply(i).compareTo(number) < 1; i = i.add(two)) {
            if (BigInteger.ZERO.equals(number.mod(i))) {
                _cache.CacheValue(number, false);
                return false;
            }
        }

        _cache.CacheValue(number, true);
        return true;
    }
}

