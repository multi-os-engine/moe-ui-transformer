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

package org.moe.transformer;

import org.moe.transformer.UIOSxProcessor.OSXStringProcessor;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CmdParser {

    private final static String xib_file = "XIBTemplate.xsl";
    private final static String storyboard_file = "StoryboardTemplate.xsl";

    private static Map<String, String> cmd_params = new HashMap<String, String>();
    private static String default_res_path = null; //Paths.get(".").toAbsolutePath().normalize().toString() + "/src/main/resources/UITransformer-res/res-designer";


    public static void InitParser(String[] params) throws IOException {

        cmd_params.put("device_type", "any");
        for (String param : params) {
            String[] pair = param.split("=");
            switch (pair[0]) {
                case "--ixml-res-path":
                    OSXStringProcessor.SetProjectResPath(pair[1]);
                    cmd_params.put("ixml_res_path", pair[1]);
                    break;
                case "--out-path":
                    cmd_params.put("out_path", pair[1]);
                    break;
                case "--out-filename":
                    cmd_params.put("out_filename", pair[1]);
                    break;
                case "--out-format":
                    if (pair[1].equals("xib")) {
                        cmd_params.put("out_format", "xib");
                        cmd_params.put("xslt_template", xib_file);
                    } else {
                        cmd_params.put("out_format", "storyboard");
                        cmd_params.put("xslt_template", storyboard_file);
                    }
                    break;
                case "--uitransformer-res-path":
                    cmd_params.put("xslt_res_path", pair[1] + "/IOSUITemplates");
                    default_res_path = pair[1] + "/res-designer";
                    break;

                case "--device_type":
                    cmd_params.put("device_type", pair[1]);
                    break;
            }
        }

        if (default_res_path == null) {
            String fileConf = null;
            BufferedReader br = null;
            try {
                String home_path = System.getenv("MOE_HOME");
                if (home_path != null) {

                    fileConf = home_path + "/.xrtconfig";
                    br = new BufferedReader(new FileReader(fileConf));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        String[] pare = line.split("=");
                        if (pare.length == 2) {
                            if (pare[0].equals("ANDROID_STUDIO_HOME")) {
                                if (new File(pare[1] + "/Contents/plugins/xrt_uiprototyper_plugin/lib/custom_elements").exists())
                                    default_res_path = pare[1] + "/Contents/plugins/xrt_uiprototyper_plugin/lib/custom_elements";
                            }
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                throw new IOException("Failed to find " + fileConf + "!\nInstall MOE again!\n" + e.getMessage(), e);
            } catch (IOException e) {
                throw new IOException("Failed to read from " + fileConf + "!\nInstall MOE again!\n" + e.getMessage(), e);
            }
            finally {
                if(br != null){
                    br.close();
                }
            }
        }

        if (default_res_path != null) {
            OSXStringProcessor.SetXsltResPath(default_res_path);
        }
        else {
            throw new NullPointerException("Failed to find UI Transformer resources path! Check --uitransformer-res-path option! ");
        }
    }

    static String GetParam(String param) {
        return cmd_params.get(param);
    }
}
