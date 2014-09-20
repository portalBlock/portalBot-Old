/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot.webinterface;


import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

/**
 * Created by portalBlock on 9/10/2014.
 */
//Credit: http://stackoverflow.com/questions/5512378/how-to-get-ram-size-and-size-of-hard-disk-using-java
//Credit: http://stackoverflow.com/questions/25552/get-os-level-system-information
public class SystemUtils {

    private static OperatingSystemMXBean osMxBean;
    private static int  availableProcessors = osMxBean.getAvailableProcessors();
    private static long lastSystemTime      = 0;
    private static long lastProcessCpuTime  = 0;

    static {
        osMxBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    }

    public static synchronized Double getCpuUsage(){
        if ( lastSystemTime == 0 ){
            baselineCounters();
            return 0D;
        }

        long systemTime     = System.nanoTime();
        long processCpuTime = osMxBean.getProcessCpuTime();

        double cpuUsage = (double) ( processCpuTime - lastProcessCpuTime ) / ( systemTime - lastSystemTime );

        lastSystemTime     = systemTime;
        lastProcessCpuTime = processCpuTime;

        return cpuUsage / availableProcessors;
    }

    private static void baselineCounters() {
        lastSystemTime = System.nanoTime();
        lastProcessCpuTime = osMxBean.getProcessCpuTime();
    }

    public static String OSname() {
        return System.getProperty("os.name");
    }

    public static String OSversion() {
        return System.getProperty("os.version");
    }

    public static String OSArch() {
        return System.getProperty("os.arch");
    }

    public static long totalMem() {
        return osMxBean.getTotalPhysicalMemorySize();
    }

    public static long usedMem() {
        return totalMem() - osMxBean.getFreePhysicalMemorySize();
    }
}
