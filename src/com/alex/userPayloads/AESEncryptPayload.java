package com.alex.userPayloads;

import burp.BurpExtender;
import burp.IIntruderPayloadProcessor;
import com.alex.bean.AESBean;
import com.alex.utils.AESUtils;
import com.alex.utils.ConvertUtils;

import javax.crypto.Cipher;
import java.io.PrintWriter;

/**
 * 此方法为intruder-payloads-payload processing中功能。
 */
public class AESEncryptPayload implements IIntruderPayloadProcessor {
    private final PrintWriter stdout;
    private BurpExtender burpExtender;

    public AESEncryptPayload(BurpExtender burpExtender) {
        this.burpExtender = burpExtender;
        this.stdout = burpExtender.stdout;
    }

    @Override
    public String getProcessorName() {
        return "AES encrypt";
    }

    /**
     * 主要重写这个方法。
     * @param currentPayload The value of the payload to be processed.
     * @param originalPayload The value of the original payload prior to
     * processing by any already-applied processing rules.
     * @param baseValue The base value of the payload position, which will be
     * replaced with the current payload.
     * @return
     */
    @Override
    public byte[] processPayload(byte[] currentPayload, byte[] originalPayload, byte[] baseValue) {
        AESBean aesBean = burpExtender.getAesBean();
        byte[] result = AESUtils.crypt(aesBean.getAlgorithm(), Cipher.ENCRYPT_MODE, originalPayload, aesBean.getKey(), aesBean.getIv());
        switch (aesBean.getOutputEncryptFormat()) {
            case "Hex String":
                result = ConvertUtils.stringToBytes(ConvertUtils.bytesToHex(result));
                break;
            case "Base64 String":
                result = ConvertUtils.stringToBytes(ConvertUtils.bytesToBase64(result));
                break;
        }
        stdout.println("laji->" + ConvertUtils.bytesToHex(result));
        return result;
    }
}
