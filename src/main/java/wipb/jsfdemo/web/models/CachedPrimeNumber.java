package wipb.jsfdemo.web.models;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Date;

public class CachedPrimeNumber {
    private BigInteger Number;
    private Boolean IsPrime;

    private Date CreatedDate;

    public CachedPrimeNumber(BigInteger number, Boolean isPrime) {
        Number = number;
        IsPrime = isPrime;
        CreatedDate = Date.from(Instant.now());
    }

    public BigInteger getNumber() {
        return Number;
    }

    public void setNumber(BigInteger number) {
        Number = number;
    }

    public Boolean getPrime() {
        return IsPrime;
    }

    public void setPrime(Boolean prime) {
        IsPrime = prime;
    }

    public Date getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(Date createdDate) {
        CreatedDate = createdDate;
    }
}
