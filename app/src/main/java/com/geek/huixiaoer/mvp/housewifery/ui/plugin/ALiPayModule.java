package com.geek.huixiaoer.mvp.housewifery.ui.plugin;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtension;
import io.rong.imkit.emoticon.IEmoticonTab;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

/**
 * 支付宝支付
 * Created by Administrator on 2018/3/8.
 */

public class ALiPayModule implements IExtensionModule {

    @Override
    public void onInit(String s) {

    }

    @Override
    public void onConnect(String s) {

    }

    @Override
    public void onAttachedToExtension(RongExtension rongExtension) {

    }

    @Override
    public void onDetachedFromExtension() {

    }

    @Override
    public void onReceivedMessage(Message message) {

    }

    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        List<IPluginModule> pluginModules = new ArrayList<>();
        ALiPayPlugin aLiPayPlugin = new ALiPayPlugin();
        pluginModules.add(aLiPayPlugin);
        return pluginModules;
    }

    @Override
    public List<IEmoticonTab> getEmoticonTabs() {
        return null;
    }

    @Override
    public void onDisconnect() {

    }
}
