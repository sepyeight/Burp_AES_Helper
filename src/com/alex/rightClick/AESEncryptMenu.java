package com.alex.rightClick;

import burp.BurpExtender;
import burp.IBurpExtenderCallbacks;
import burp.IContextMenuInvocation;
import burp.IHttpRequestResponse;
import com.alex.bean.AESBean;
import com.alex.utils.AESUtils;
import com.alex.utils.ConvertUtils;

import javax.crypto.Cipher;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.io.PrintWriter;

/**
 * 此方法为AESUtils右键功能。
 */
public class AESEncryptMenu extends JMenu implements ActionListener {

    private final BurpExtender burpExtender;
    private final IBurpExtenderCallbacks callbacks;
    private PrintWriter stdout;
    private IContextMenuInvocation invocation;
    private AESBean aesBean;
    private String[] aesEncryptMenuItemStrArray = new String[]{"Str2Hex", "Str2Base64", "Hex2Hex", "Hex2Base64", "Base642Hex", "Base642Base64"};
    private String resultStr;
    private IHttpRequestResponse reqResp;

    public AESEncryptMenu(BurpExtender burpExtender, IContextMenuInvocation invocation) {
        this.burpExtender = burpExtender;
        this.callbacks = burpExtender.callbacks;
        this.stdout = burpExtender.stdout;
        this.invocation = invocation;
        addMenuItemAndListener(aesEncryptMenuItemStrArray);
        aesBean = this.burpExtender.getAesBean();
        addActionListener(this);
        stdout.println(aesBean.toString());
        this.setText("AES Encrypt");
    }

    private void addMenuItemAndListener(String[] aesEncryptMenuItemStrArray) {
        for (String menuName : aesEncryptMenuItemStrArray) {
            JMenuItem menuItem = new JMenuItem(menuName);
            this.add(menuItem);
            menuItem.addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        String selectedStr = getSelectedStr();
        byte[] inputBytes;
        byte[] resultBytes;
        switch (action) {
            case "Str2Hex":
                inputBytes = ConvertUtils.stringToBytes(selectedStr);
                resultBytes = AESUtils.crypt(aesBean.getAlgorithm(), Cipher.ENCRYPT_MODE, inputBytes, aesBean.getKey(), aesBean.getIv());
                resultStr = ConvertUtils.bytesToHex(resultBytes);
                setSelectedStr(resultStr);
                break;
            case "Str2Base64":
                inputBytes = ConvertUtils.stringToBytes(selectedStr);
                resultBytes = AESUtils.crypt(aesBean.getAlgorithm(), Cipher.ENCRYPT_MODE, inputBytes, aesBean.getKey(), aesBean.getIv());
                resultStr = ConvertUtils.bytesToBase64(resultBytes);
                setSelectedStr(resultStr);
                break;
            case "Hex2Hex":
                inputBytes = ConvertUtils.hexToBytes(selectedStr);
                resultBytes = AESUtils.crypt(aesBean.getAlgorithm(), Cipher.ENCRYPT_MODE, inputBytes, aesBean.getKey(), aesBean.getIv());
                resultStr = ConvertUtils.bytesToHex(resultBytes);
                setSelectedStr(resultStr);
                break;
            case "Hex2Base64":
                inputBytes = ConvertUtils.hexToBytes(selectedStr);
                resultBytes = AESUtils.crypt(aesBean.getAlgorithm(), Cipher.ENCRYPT_MODE, inputBytes, aesBean.getKey(), aesBean.getIv());
                resultStr = ConvertUtils.bytesToBase64(resultBytes);
                setSelectedStr(resultStr);
                break;
            case "Base642Hex":
                inputBytes = ConvertUtils.base64ToBytes(selectedStr);
                resultBytes = AESUtils.crypt(aesBean.getAlgorithm(), Cipher.ENCRYPT_MODE, inputBytes, aesBean.getKey(), aesBean.getIv());
                resultStr = ConvertUtils.bytesToHex(resultBytes);
                setSelectedStr(resultStr);
                break;
            case "Base642Base64":
                inputBytes = ConvertUtils.base64ToBytes(selectedStr);
                resultBytes = AESUtils.crypt(aesBean.getAlgorithm(), Cipher.ENCRYPT_MODE, inputBytes, aesBean.getKey(), aesBean.getIv());
                resultStr = ConvertUtils.bytesToBase64(resultBytes);
                setSelectedStr(resultStr);
                break;

        }
    }

    private String getSelectedStr() {
        int[] selectedIndex = invocation.getSelectionBounds();
        byte[] param = new byte[selectedIndex[1] - selectedIndex[0]];
        reqResp= invocation.getSelectedMessages()[0];
        byte[] request = reqResp.getRequest();
        System.arraycopy(request, selectedIndex[0], param, 0, selectedIndex[1] - selectedIndex[0]);
        return new String(param);
    }

    private void setSelectedStr(String addStr) {
        JOptionPane.showInputDialog("Encrypt", addStr);
    }
}
