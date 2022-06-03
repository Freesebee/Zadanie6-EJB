package wipb.jsfdemo.web.service;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

public class Interceptor {
    @AroundInvoke
    Object logTime(InvocationContext ic) throws Exception {
        long entryTime = System.currentTimeMillis();
        Object res = null;

        try {
            /* wywołanie docelowej metody */
            res = ic.proceed();
        } finally {
            Logger.getLogger(ic.getTarget().getClass().getName()).fine(String.valueOf(System.currentTimeMillis() - entryTime));
            return res;
        }
    }

}
