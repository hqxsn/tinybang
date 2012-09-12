/**
 *    Copyright [2011] TinyBang Licensed under the Apache License, Version 2.0.
 */
package com.google.tinybang.queue.channel.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @author <a href="mailto:wz.xjtu@gmail.com">wenzhong</a>
 * Jul 6, 2010
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommonServerChannelConfigs", propOrder = {
    "configs"
})
public class CommonServerChannelConfigs {

	List<CommonServerChannelConfig> configs;

	public List<CommonServerChannelConfig> getConfigs() {
		if (configs == null)
			configs = new ArrayList<CommonServerChannelConfig>();
		return configs;
	}

	public void setConfigs(List<CommonServerChannelConfig> configs) {
		this.configs = configs;
	}
	
	
	
}
