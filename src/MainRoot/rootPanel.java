package MainRoot;

import burp.BurpExtender;
import burp.IBurpExtenderCallbacks;
import com.alex.bean.AESBean;
import com.alex.utils.AESUtils;
import com.alex.utils.ConvertUtils;

import javax.crypto.Cipher;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * idea画的界面，实现界面中的方法。
 */
public class rootPanel {
    public JPanel rootPanel;
    private JComboBox cryptMode;
    private JComboBox algorithmMode;
    private JComboBox paddingMode;
    private JLabel keyFormat;
    private JComboBox keyFormatMode;
    private JComboBox ivFormatMode;
    private JLabel inputEncrypt;
    private JComboBox inputEncryptMode;
    private JTextArea inputEncryptText;
    private JButton encryptButton;
    private JLabel outputEncrypt;
    private JComboBox outputEncryptMode;
    private JTextArea outputEncryptText;
    private JLabel inputDecrypt;
    private JComboBox inputDecryptMode;
    private JTextArea inputDecryptText;
    private JButton decryptButton;
    private JComboBox outputDecryptMode;
    private JTextArea outputDecryptText;
    private JPanel keySelect;
    private JEditorPane ivFormatText;
    private JEditorPane keyFormatText;
    private JLabel ivFormat;
    private JPanel algorithmSelect;
    private JCheckBox enableGlobal;
    private String cryptMethodText;
    private String inputEncryptModeText;
    private String outputEncryptModeText;
    private String inputDecryptModeText;
    private String outputDecryptModeText;
    private byte[] ivFormatTextBytes;
    private byte[] keyFormatTextBytes;
    private byte[] inputEncryptBytes;
    private byte[] inputDecryptBytes;
    private BurpExtender burpExtender;
    private IBurpExtenderCallbacks callbacks;


    public rootPanel(BurpExtender burpExtender) {
        this.burpExtender = burpExtender;
        this.callbacks = burpExtender.callbacks;
        //保存到本地
//        callbacks.saveToTempFile();
        encryptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                outputEncryptText.setText("");
                getAlgorithmKeyAndIV();
                getInputAndOutputFormat();
                doEncrypt();
            }
        });
        decryptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                outputDecryptText.setText("");
                getAlgorithmKeyAndIV();
                getInputAndOutputFormat();
                doDecrypt();
            }
        });

        enableGlobal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (enableGlobal.isSelected()) {
                    getAlgorithmKeyAndIV();
                    getInputAndOutputFormat();
                    AESBean tempAESBean = createTempAESBean();
                    burpExtender.setAesBean(tempAESBean);
//                    String jsonObject = new Gson().toJson(tempAESBean);
//                    callbacks.saveExtensionSetting("AES Utils", jsonObject);
                }
            }
        });
    }

    private AESBean createTempAESBean() {
        AESBean aesBean = new AESBean();
        aesBean.setAlgorithm(cryptMethodText);
        aesBean.setKey(keyFormatTextBytes);
        aesBean.setIv(ivFormatTextBytes);
        aesBean.setInputEncryptFormat(inputEncryptModeText);
        aesBean.setInputEncryptStr(inputEncryptText.getText());
        aesBean.setOutputEncryptFormat(outputEncryptModeText);
        aesBean.setInputDecryptFormat(inputDecryptModeText);
        aesBean.setInputDecryptStr(inputDecryptText.getText());
        aesBean.setOutputDecryptFormat(outputDecryptModeText);
        System.out.println(aesBean.toString());
        return aesBean;
    }

    public rootPanel() {
        encryptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                outputEncryptText.setText("");
                getAlgorithmKeyAndIV();
                getInputAndOutputFormat();
                doEncrypt();
            }
        });
        decryptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                outputDecryptText.setText("");
                getAlgorithmKeyAndIV();
                getInputAndOutputFormat();
                doDecrypt();
            }
        });
        enableGlobal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (enableGlobal.isSelected()) {
                    createTempAESBean();
                } else {
                    removeTempFile();
                }
            }
        });
    }

    private void removeTempFile() {

    }

    private void saveToTempFile() {
        AESBean aesBean = new AESBean();
        aesBean.setAlgorithm(cryptMethodText);
        aesBean.setKey(keyFormatTextBytes);
        aesBean.setIv(ivFormatTextBytes);
        aesBean.setInputEncryptFormat(inputEncryptModeText);
        aesBean.setInputEncryptStr(inputEncryptText.getText());
        aesBean.setOutputEncryptFormat(outputEncryptModeText);
        aesBean.setInputDecryptFormat(inputDecryptModeText);
        aesBean.setInputDecryptStr(inputDecryptText.getText());
        aesBean.setOutputDecryptFormat(outputDecryptModeText);
        System.out.println(aesBean.toString());
    }

    private void doDecrypt() {

        byte[] outputDecryptBytes = AESUtils.crypt(cryptMethodText, Cipher.DECRYPT_MODE, inputDecryptBytes, keyFormatTextBytes, ivFormatTextBytes);
        switch (outputDecryptModeText) {
            case "Hex String":
                outputDecryptText.setText(ConvertUtils.bytesToHex(outputDecryptBytes));
                break;
            case "Base64 String":
                outputDecryptText.setText(ConvertUtils.bytesToBase64(outputDecryptBytes));
                break;
            case "String":
                outputDecryptText.setText(ConvertUtils.bytesToString(outputDecryptBytes));
                break;
        }
    }

    private void doEncrypt() {

        byte[] outputEncryptBytes = AESUtils.crypt(cryptMethodText, Cipher.ENCRYPT_MODE, inputEncryptBytes, keyFormatTextBytes, ivFormatTextBytes);
        switch (outputEncryptModeText) {
            case "Hex String":
                outputEncryptText.setText(ConvertUtils.bytesToHex(outputEncryptBytes));
                break;
            case "Base64 String":
                outputEncryptText.setText(ConvertUtils.bytesToBase64(outputEncryptBytes));
                break;
        }
    }

    private void getInputAndOutputFormat() {
        inputEncryptModeText = inputEncryptMode.getSelectedItem().toString();
        switch (inputEncryptModeText) {
            case "String":
                inputEncryptBytes = ConvertUtils.stringToBytes(inputEncryptText.getText());
                break;
            case "Hex String":
                inputEncryptBytes = ConvertUtils.hexToBytes(inputEncryptText.getText());
                break;
            case "Base64 String":
                inputEncryptBytes = ConvertUtils.base64ToBytes(inputEncryptText.getText());
                break;
        }
        outputEncryptModeText = outputEncryptMode.getSelectedItem().toString();
        inputDecryptModeText = inputDecryptMode.getSelectedItem().toString();
        switch (inputDecryptModeText) {
            case "String":
                inputDecryptBytes = ConvertUtils.stringToBytes(inputDecryptText.getText());
                break;
            case "Hex String":
                inputDecryptBytes = ConvertUtils.hexToBytes(inputDecryptText.getText());
                break;
            case "Base64 String":
                inputDecryptBytes = ConvertUtils.base64ToBytes(inputDecryptText.getText());
                break;
        }
        outputDecryptModeText = outputDecryptMode.getSelectedItem().toString();
        System.out.println("encrypt->" + inputEncryptModeText + ", " + outputEncryptModeText + ", decrypt->" + inputDecryptModeText + ", " + outputDecryptModeText);
    }

    private void getAlgorithmKeyAndIV() {
        getAlgorithmSelect();
        getKeySelect();
        getIVSelect();
    }

    private void getIVSelect() {
        String ivFormatModeText = ivFormatMode.getSelectedItem().toString();
        String ivFormatTextStr = ivFormatText.getText();
        switch (ivFormatModeText) {
            case "String":
                ivFormatTextBytes = ConvertUtils.stringToBytes(ivFormatTextStr);
                break;
            case "Hex String":
                ivFormatTextBytes = ConvertUtils.hexToBytes(ivFormatTextStr);
                break;
            case "Base64 String":
                ivFormatTextBytes = ConvertUtils.base64ToBytes(ivFormatTextStr);
                break;
        }
        System.out.println("iv->" + ivFormatModeText + ", " + ivFormatTextStr);
    }

    private void getKeySelect() {
        String keyFormatModeText = keyFormatMode.getSelectedItem().toString();
        String keyFormatTextStr = keyFormatText.getText().toString();
        switch (keyFormatModeText) {
            case "String":
                keyFormatTextBytes = ConvertUtils.stringToBytes(keyFormatTextStr);
                break;
            case "Hex String":
                keyFormatTextBytes = ConvertUtils.hexToBytes(keyFormatTextStr);
                break;
            case "Base64 String":
                keyFormatTextBytes = ConvertUtils.base64ToBytes(keyFormatTextStr);
                break;
        }
        System.out.println("key->" + keyFormatModeText + ", " + keyFormatTextStr);
    }

    private void getAlgorithmSelect() {
        String algorithmModeText = algorithmMode.getSelectedItem().toString();
        String cryptModeText = cryptMode.getSelectedItem().toString();
        String paddingModeText = paddingMode.getSelectedItem().toString();
        cryptMethodText = algorithmModeText + "/" + cryptModeText + "/" + paddingModeText;
        System.out.println("algorithm->" + cryptMethodText);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("rootPanel");
        frame.setContentPane(new rootPanel().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
