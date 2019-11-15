package com.alex.factory;

import burp.*;
import com.alex.generator.*;
/**
 * 此模块为intruder-payloads-payload sets写法，
 * 先写一个方法继承IIntruderPayloadGeneratorFactory，
 * 重写createNewInstance方法，写一个新类继承IIntruderPayloadGenerator，重写其中的方法。
 */
public class AESEncryptFactory implements IIntruderPayloadGeneratorFactory {

    private final BurpExtender burpExtender;
    private final IBurpExtenderCallbacks callbacks;

    public AESEncryptFactory(BurpExtender burpExtender) {
        this.burpExtender = burpExtender;
        this.callbacks = burpExtender.callbacks;
    }

    /**
     * 返回名称
     * @return
     */
    @Override
    public String getGeneratorName() {
        return "AESEncrypt";
    }

    @Override
    public IIntruderPayloadGenerator createNewInstance(IIntruderAttack attack) {
        return new AESEncryptGenerator(this.burpExtender);
    }
}
