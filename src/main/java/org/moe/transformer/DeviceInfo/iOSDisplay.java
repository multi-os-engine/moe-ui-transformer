/*
Copyright 2014-2016 Intel Corporation

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package org.moe.transformer.DeviceInfo;
import java.util.HashMap;
import java.util.Map;

public class iOSDisplay {

	private static Map<eiOSDeviceName, Display> displays = null;

	static {
		displays = new HashMap<eiOSDeviceName, Display>();
		displays.put(eiOSDeviceName.iphone3g, new Display(320, 480));
		displays.put(eiOSDeviceName.iphone4, new Display(320, 480));
		displays.put(eiOSDeviceName.iphone4s, new Display(320, 480));
		displays.put(eiOSDeviceName.iphone5, new Display(320, 560));
		displays.put(eiOSDeviceName.iphone5c, new Display(320, 560));
		displays.put(eiOSDeviceName.iphone5s, new Display(320, 560));
		displays.put(eiOSDeviceName.iphone6, new Display(375, 667));
		displays.put(eiOSDeviceName.iphone6pluse, new Display(414, 736));
		displays.put(eiOSDeviceName.ipad, new Display(1024, 768));
		displays.put(eiOSDeviceName.ipadretina, new Display(2048, 1536));
		displays.put(eiOSDeviceName.any, new Display(600, 600));

	}
	
	public static Display GetDeviceDisplay(eiOSDeviceName dev){
		return displays.get(dev);
	}

	public static eiOSDeviceName GetEIOSDeviceName(String deviceName){

		if(deviceName.equals("any"))
			return eiOSDeviceName.any;
		else if(deviceName.equals("iphone3g"))
			return eiOSDeviceName.iphone3g;
		else if(deviceName.equals("iphone4"))
			return eiOSDeviceName.iphone4;
		else if(deviceName.equals("iphone4s"))
			return eiOSDeviceName.iphone4s;
		else if(deviceName.equals("iphone5"))
			return eiOSDeviceName.iphone5;
		else if(deviceName.equals("iphone5c"))
			return eiOSDeviceName.iphone5c;
		else if(deviceName.equals("iphone5s"))
			return eiOSDeviceName.iphone5s;
		else if(deviceName.equals("iphone6"))
			return eiOSDeviceName.iphone6;
		else if(deviceName.equals("iphone6pluse"))
			return eiOSDeviceName.iphone6pluse;
		else if(deviceName.equals("ipad"))
			return eiOSDeviceName.ipad;
		else if(deviceName.equals("ipadretina"))
			return eiOSDeviceName.ipadretina;

		return eiOSDeviceName.any;
	}
}
