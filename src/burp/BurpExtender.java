package burp;

import MainRoot.rootPanel;
import com.alex.bean.AESBean;
import com.alex.rightClick.AESDecryptMenu;
import com.alex.rightClick.AESEncryptMenu;
import com.alex.userPayloads.AESEncryptPayload;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 1. 写burp插件，自写一个BurpExtender类请先继承IBurpExtender，ITab
 * 2.
 */

public class BurpExtender implements IBurpExtender, IContextMenuFactory, ITab, IHttpListener, IProxyListener, IExtensionStateListener {
    private String EXTENSIONNAME = "AES Helper";
    public IBurpExtenderCallbacks callbacks;
    public IExtensionHelpers helpers;
    public PrintWriter stdout;
    public JPanel jPanel;
    private AESBean aesBean;

    public AESBean getAesBean() {
        return aesBean;
    }

    public void setAesBean(AESBean aesBean) {
        this.aesBean = aesBean;
    }

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
        this.helpers = callbacks.getHelpers();
        // burp信息输出
        this.stdout = new PrintWriter(callbacks.getStdout(), true);
        // Extender-extensions插件中名称
        this.callbacks.setExtensionName(this.EXTENSIONNAME);
        this.callbacks.registerContextMenuFactory(this);
//        this.callbacks.registerIntruderPayloadGeneratorFactory(new AESEncryptFactory(this));
        // 注册burp intruder-payloads-payload processing中模块
        this.callbacks.registerIntruderPayloadProcessor(new AESEncryptPayload(this));

        //输出安装信息
        this.stdout.println("AES HELPER installed!");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //使用idea画好界面，然后在这里注册
                jPanel = new rootPanel(BurpExtender.this).rootPanel;
                callbacks.customizeUiComponent(jPanel);
                callbacks.addSuiteTab(BurpExtender.this);
                callbacks.registerExtensionStateListener(BurpExtender.this);
            }
        });

    }

    // 返回模块名称
    @Override
    public String getTabCaption() {
        return this.EXTENSIONNAME;
    }

    // 返回自写的模块对象
    @Override
    public Component getUiComponent() {
        return this.jPanel;
    }

    // 右键功能编写
    @Override
    public List<JMenuItem> createMenuItems(IContextMenuInvocation invocation) {
        ArrayList<JMenuItem> menuItems = new ArrayList<>();
        JMenu aesUtils = new JMenu("AES Utils");
        aesUtils.add(new AESEncryptMenu(this, invocation));
        aesUtils.add(new AESDecryptMenu(this, invocation));
        menuItems.add(aesUtils);
        return menuItems;
    }

    @Override
    public void extensionUnloaded() {

    }

    @Override
    public void processHttpMessage(int toolFlag, boolean messageIsRequest, IHttpRequestResponse messageInfo) {

    }

    @Override
    public void processProxyMessage(boolean messageIsRequest, IInterceptedProxyMessage message) {

    }
}
