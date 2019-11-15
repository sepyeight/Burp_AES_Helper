package com.alex.bean;

import com.alex.utils.ConvertUtils;

/**
 * 自写界面信息对象
 */
public class AESBean {
    private String algorithm;
    private byte[] key;
    private byte[] iv;
    private String inputEncryptFormat;
    private String inputEncryptStr;
    private String outputEncryptFormat;
    private String outputEncryptStr;
    private String inputDecryptFormat;
    private String inputDecryptStr;
    private String outputDecryptFormat;
    private String outputDecryptStr;

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    public String getInputEncryptFormat() {
        return inputEncryptFormat;
    }

    public void setInputEncryptFormat(String inputEncryptFormat) {
        this.inputEncryptFormat = inputEncryptFormat;
    }

    public String getInputEncryptStr() {
        return inputEncryptStr;
    }

    public void setInputEncryptStr(String inputEncryptStr) {
        this.inputEncryptStr = inputEncryptStr;
    }

    public String getOutputEncryptFormat() {
        return outputEncryptFormat;
    }

    public void setOutputEncryptFormat(String outputEncryptFormat) {
        this.outputEncryptFormat = outputEncryptFormat;
    }

    public String getOutputEncryptStr() {
        return outputEncryptStr;
    }

    public void setOutputEncryptStr(String outputEncryptStr) {
        this.outputEncryptStr = outputEncryptStr;
    }

    public String getInputDecryptFormat() {
        return inputDecryptFormat;
    }

    public void setInputDecryptFormat(String inputDecryptFormat) {
        this.inputDecryptFormat = inputDecryptFormat;
    }

    public String getInputDecryptStr() {
        return inputDecryptStr;
    }

    public void setInputDecryptStr(String inputDecryptStr) {
        this.inputDecryptStr = inputDecryptStr;
    }

    public String getOutputDecryptFormat() {
        return outputDecryptFormat;
    }

    public void setOutputDecryptFormat(String outputDecryptFormat) {
        this.outputDecryptFormat = outputDecryptFormat;
    }

    public String getOutputDecryptStr() {
        return outputDecryptStr;
    }

    public void setOutputDecryptStr(String outputDecryptStr) {
        this.outputDecryptStr = outputDecryptStr;
    }

    @Override
    public String toString() {
        return "AESBean{" +
                "algorithm='" + algorithm + '\'' +
                ", key=" + ConvertUtils.bytesToBase64(key) +
                ", iv=" + ConvertUtils.bytesToBase64(iv) +
                ", inputEncryptFormat='" + inputEncryptFormat + '\'' +
                ", inputEncryptStr='" + inputEncryptStr + '\'' +
                ", outputEncryptFormat='" + outputEncryptFormat + '\'' +
                ", outputEncryptStr='" + outputEncryptStr + '\'' +
                ", inputDecryptFormat='" + inputDecryptFormat + '\'' +
                ", inputDecryptStr='" + inputDecryptStr + '\'' +
                ", outputDecryptFormat='" + outputDecryptFormat + '\'' +
                ", outputDecryptStr='" + outputDecryptStr + '\'' +
                '}';
    }
}
