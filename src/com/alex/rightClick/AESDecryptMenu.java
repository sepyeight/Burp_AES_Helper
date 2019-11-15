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
import java.io.PrintWriter;

import static burp.IContextMenuInvocation.CONTEXT_MESSAGE_EDITOR_REQUEST;
import static burp.IContextMenuInvocation.CONTEXT_MESSAGE_VIEWER_RESPONSE;

/**
 * 此方法为AESUtils右键功能。
 */
public class AESDecryptMenu extends JMenu implements ActionListener {

    private final BurpExtender burpExtender;
    private final IBurpExtenderCallbacks callbacks;
    private PrintWriter stdout;
    private IContextMenuInvocation invocation;
    private AESBean aesBean;
    private String[] aesDecryptMenuItemStrArray = new String[]{"Hex2Str", "Base642str", "Hex2Hex", "Base642Hex", "Hex2Base64", "Base642Base64"};
    private String resultStr;
    private IHttpRequestResponse reqResp;

    public AESDecryptMenu(BurpExtender burpExtender, IContextMenuInvocation invocation) {
        this.burpExtender = burpExtender;
        this.callbacks = burpExtender.callbacks;
        this.stdout = burpExtender.stdout;
        this.invocation = invocation;
        addMenuItemAndListener(aesDecryptMenuItemStrArray);
        aesBean = this.burpExtender.getAesBean();
        addActionListener(this);
        stdout.println(aesBean.toString());
        this.setText("AES Decrypt");
    }

    private void addMenuItemAndListener(String[] aesDecryptMenuItemStrArray) {
        for (String menuName : aesDecryptMenuItemStrArray) {
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
            case "Hex2Str":
                inputBytes = ConvertUtils.hexToBytes(selectedStr);
                resultBytes = AESUtils.crypt(aesBean.getAlgorithm(), Cipher.DECRYPT_MODE, inputBytes, aesBean.getKey(), aesBean.getIv());
                resultStr = ConvertUtils.bytesToString(resultBytes);
                setSelectedStr(resultStr);
                break;
            case "Base642str":
                inputBytes = ConvertUtils.base64ToBytes(selectedStr);
                resultBytes = AESUtils.crypt(aesBean.getAlgorithm(), Cipher.DECRYPT_MODE, inputBytes, aesBean.getKey(), aesBean.getIv());
                resultStr = ConvertUtils.bytesToString(resultBytes);
                setSelectedStr(resultStr);
                break;
            case "Hex2Hex":
                inputBytes = ConvertUtils.hexToBytes(selectedStr);
                resultBytes = AESUtils.crypt(aesBean.getAlgorithm(), Cipher.DECRYPT_MODE, inputBytes, aesBean.getKey(), aesBean.getIv());
                resultStr = ConvertUtils.bytesToHex(resultBytes);
                setSelectedStr(resultStr);
                break;
            case "Base642Hex":
                inputBytes = ConvertUtils.base64ToBytes(selectedStr);
                resultBytes = AESUtils.crypt(aesBean.getAlgorithm(), Cipher.DECRYPT_MODE, inputBytes, aesBean.getKey(), aesBean.getIv());
                resultStr = ConvertUtils.bytesToHex(resultBytes);
                setSelectedStr(resultStr);
                break;
            case "Hex2Base64":
                inputBytes = ConvertUtils.hexToBytes(selectedStr);
                resultBytes = AESUtils.crypt(aesBean.getAlgorithm(), Cipher.DECRYPT_MODE, inputBytes, aesBean.getKey(), aesBean.getIv());
                resultStr = ConvertUtils.bytesToBase64(resultBytes);
                setSelectedStr(resultStr);
                break;
            case "Base642Base64":
                inputBytes = ConvertUtils.base64ToBytes(selectedStr);
                resultBytes = AESUtils.crypt(aesBean.getAlgorithm(), Cipher.DECRYPT_MODE, inputBytes, aesBean.getKey(), aesBean.getIv());
                resultStr = ConvertUtils.bytesToBase64(resultBytes);
                setSelectedStr(resultStr);
                break;

        }
    }

    private String getSelectedStr() {
        byte selectedMenu = invocation.getInvocationContext();
        int[] selectedIndex = invocation.getSelectionBounds();
        byte[] param = new byte[selectedIndex[1] - selectedIndex[0]];
        reqResp = invocation.getSelectedMessages()[0];
        if (selectedMenu == CONTEXT_MESSAGE_VIEWER_RESPONSE) {
            byte[] response = reqResp.getResponse();
            System.arraycopy(response, selectedIndex[0], param, 0, selectedIndex[1] - selectedIndex[0]);
        } else {
            byte[] request = reqResp.getRequest();
            System.arraycopy(request, selectedIndex[0], param, 0, selectedIndex[1] - selectedIndex[0]);
        }
        return new String(param);
    }

    private void setSelectedStr(String addStr) {
        JOptionPane.showInputDialog("Decrypt", addStr);
//        int[] selectedIndex = invocation.getSelectionBounds();
//        IHttpRequestResponse req = invocation.getSelectedMessages()[0];
//        byte[] request = req.getRequest();
//        byte[] temp = new byte[request.length + addStr.length() - (selectedIndex[1] - selectedIndex[0])];
//        System.arraycopy(request, 0, temp, 0, selectedIndex[0]);
//        System.arraycopy(addStr.getBytes(), 0, temp, selectedIndex[0], addStr.getBytes().length);
//        System.arraycopy(request, selectedIndex[1], temp, selectedIndex[0] + addStr.getBytes().length, request.length - selectedIndex[1]);
//        reqResp.setRequest(temp);
    }
}
