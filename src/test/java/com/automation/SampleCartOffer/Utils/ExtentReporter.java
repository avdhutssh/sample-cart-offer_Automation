package com.automation.SampleCartOffer.Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReporter {

    public static ExtentReports generateExtentReport() {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File reportsDir = new File(System.getProperty("user.dir") + File.separator + "Reports");
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
        }
        File extentReportFile = new File(reportsDir + File.separator + "extentReport.html");

        ExtentReports extentReport = new ExtentReports();
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(extentReportFile);

        sparkReporter.config().setTheme(Theme.DARK);

        sparkReporter.config().setReportName("Test Suite Execution for Cart Offer API");
        sparkReporter.config().setDocumentTitle("API Automation Execution Report");
        sparkReporter.config().setTimeStampFormat("dd-MM-yyyy hh:mm:ss");

        extentReport.attachReporter(sparkReporter);

        extentReport.setSystemInfo("Executed by", "Avdhut Satish Shirgaonkar");
        extentReport.setSystemInfo("Application URL", "http://localhost:9001");
        extentReport.setSystemInfo("Environment", "QA");
        extentReport.setSystemInfo("API Version", "v1");
        extentReport.setSystemInfo("Operating System", System.getProperty("os.name"));
        extentReport.setSystemInfo("Java Version", System.getProperty("java.version"));

        return extentReport;
    }
}