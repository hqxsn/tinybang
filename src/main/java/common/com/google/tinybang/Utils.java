package com.google.tinybang;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by TinyBang.
 * User: andy.song
 * Date: Apr 13, 2010
 * Time: 11:49:17 PM
 */
public class Utils {

    public static final String Empty_Oid_Value = "NA";
    public static final String Empty_CorrelationId = "NA";
    public static final String Empty_Executed_Escalation_Type = "NA";

    protected static String hostAddress;
    
    private static String serverName;
    
    protected static String serverUniqueId;

    static Object parkObject = new Object();

    @Deprecated
    public synchronized static void timeWait(long waitMilliseconds) throws InterruptedException {

        synchronized (parkObject) {
            TimeUnit unit = TimeUnit.MILLISECONDS;
            unit.timedWait(parkObject, waitMilliseconds);
        }
    }

    public static boolean isNullOrEmpty(String val) {
        return val == null || val.isEmpty();
    }

    public static boolean isNullOrEmptyAfterTrim(String val) {
        return val == null || isNullOrEmpty(new String(val.trim()));
    }

    public static boolean isNullOrEmpty(Collection<?> val) {
        return val == null || val.isEmpty();
    }

    public static boolean isNullOrEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static String getLocalNetAddress() {
        if (hostAddress == null) {

            try {
                hostAddress = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                return "127.0.0.1";
            }
        }

        return hostAddress;
    }
    
    public static String getLocalServerName() {
    	if (serverName == null) {
    		try {
    			serverName = InetAddress.getLocalHost().getHostName();
    		} catch (Exception e) {
    			return "";
    		}
    	}
    	return serverName;
    }

    public static String getUniqueId() {
    	return String.valueOf(System.nanoTime());
    }
    
    public static String outputStackTrace(Throwable ex) {
        String returnValue = "";

        ByteArrayOutputStream bos = null;
        PrintStream ps = null;
        try {
            bos = new ByteArrayOutputStream();
            ps = new PrintStream(bos, true);
            ex.printStackTrace(ps);

            ps.flush();
            bos.flush();

            returnValue = bos.toString();
        } catch (Exception ext) {
            //Swallow
        } finally {
            if (ps != null) {
                ps.close();
            }

            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                }
            }
        }

        return returnValue;
    }

    /**
     * Override the JDK trim method because the JDK trim has some issue.
     * @param string
     * @return
     */
    public static String trim(String string) {
    	if (isNullOrEmpty(string))
    		throw new IllegalArgumentException("the input string is null, please check it!");
    	return new String(string.trim());
    }
    
    /**
     * Check-in for the string check from the null to the empty.
     * 
     * @param
     * */
    public static String nullToEmpty(String string) {
    	if (string == null)
    		return "";
    	return string;
    }

}
