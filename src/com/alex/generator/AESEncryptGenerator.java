package com.alex.generator;

import burp.*;
import com.alex.bean.AESBean;

import java.io.PrintWriter;

/**
 * 此模块为intruder-payloads-payload sets写法，
 */
public class AESEncryptGenerator implements IIntruderPayloadGenerator {
    public IBurpExtenderCallbacks callbacks;
    public IExtensionHelpers helpers;
    public PrintWriter stdout;
    private AESBean aesBean;
    private int payloadIndex = 0;

    public AESEncryptGenerator(BurpExtender burpExtender) {
        this.callbacks = burpExtender.callbacks;
        this.helpers = burpExtender.helpers;
        this.stdout = burpExtender.stdout;
        this.aesBean = burpExtender.getAesBean();
    }

    @Override
    public boolean hasMorePayloads() {
        stdout.println(aesBean.toString());
        return false;
    }

    @Override
    public byte[] getNextPayload(byte[] baseValue) {
        stdout.println(new String(baseValue));
        return baseValue;
    }

    @Override
    public void reset() {
        payloadIndex = 0;
    }
}
