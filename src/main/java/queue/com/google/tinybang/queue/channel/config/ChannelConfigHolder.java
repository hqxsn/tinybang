/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Holder supports to load the configuration files and provide the service configuration.
 *
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 *         Jul 6, 2010
 */
public class ChannelConfigHolder {

    private static String _file_path = System.getProperty("channel.config.file.path");

    private static Map<String, CommonServerChannelConfig> _config_mappings = new HashMap<String, CommonServerChannelConfig>();

    private static ChannelConfigHolder _channel_config_holder = new ChannelConfigHolder();

    private ChannelConfigHolder() {
        init();
    }

    ;

    public Map<String, CommonServerChannelConfig> getConfigs() {
        return _config_mappings;
    }

    /**
     * init the configurations from the configuration files.
     */
    private void init() {

//		CBSBindingConstants.addBindingClass(CommonServerChannelConfigs.class, "CommonServerChannelConfigs");
//		CommonServerChannelConfigs configs = null;
//		try {
//			configs = JAXBComplexTypeBinding.getInstance().deserializeTheComplexTypeFromFile(_file_path, CommonServerChannelConfigs.class);
//		} catch (Exception e) {
//			SystemExitHelper.exit(MessageFormat.format("Cannot log the configuration according the configuration path {0}, please check the configuration", _file_path), e);
//		}
//
//		for (CommonServerChannelConfig config: configs.getConfigs()) {
//			_config_mappings.put(config.getQueue(), config);
//		}
    }

    public static ChannelConfigHolder getInstance() {
        return _channel_config_holder;
    }

    public CommonServerChannelConfig getConfig(String queue) {
        return _config_mappings.get(queue);
    }

}

