package com.automation.SampleCartOffer.Utils;

import com.automation.SampleCartOffer.Utils.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TestListener implements ITestListener {

    ExtentTest test;
    ExtentReports extentReport;
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

    @Override
    public void onStart(ITestContext context) {
        extentReport = ExtentReporter.generateExtentReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
        test = extentReport.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
        extentTest.get().log(Status.INFO, result.getName() + " started executing");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, result.getName() + " got successfully executed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().log(Status.INFO, result.getThrowable());
        extentTest.get().log(Status.FAIL, result.getName() + " got failed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.INFO, result.getThrowable());
        extentTest.get().log(Status.SKIP, result.getName() + " got skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReport.flush();
        String extentReportPath = System.getProperty("user.dir") + File.separator + "Reports" + File.separator + "extentReport.html";

        File extentReportFile = new File(extentReportPath);
        try {
            Desktop.getDesktop().browse(extentReportFile.toURI());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
