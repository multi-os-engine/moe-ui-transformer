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

package org.moe.transformer.UIOSxProcessor;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class OSXStringProcessor {

	public static String CreateId() {
		try {
			return UUID.randomUUID().toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return UUID.randomUUID().toString();
		}
	}

	public static String ElementIdConverter(String id) {
		try {
            if(!id.isEmpty()) {
                String ret[] = id.split("/");
                return ret[1];
            }
            else return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return UUID.randomUUID().toString();
		}
	}

	public static String ElementDrawableConverter(String drawable) {
		try {
            if(!drawable.isEmpty()) {
                String ret[] = drawable.split("/");
                return ret[1];
            }
            else return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
	}

	public static String StyleIdConverter(String id) {
		try {
            if(!id.isEmpty()) {
                String ret[] = id.split("/");
                return ret[1];
            }
            else return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
	}

	public static String FindNum(String str) {
		Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
		Matcher matcher = pat.matcher(str);
		matcher.find();
		return matcher.group();
	}

	public static String SizeFormatConverter(String fontSize) {
		try {
			return FindNum(fontSize);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "15";
		}
	}

	private static Color hex2Rgb(String colorStr) {
		return new Color(
				Integer.valueOf(colorStr.substring(1, 3), 16),
				Integer.valueOf(colorStr.substring(3, 5), 16),
				Integer.valueOf(colorStr.substring(5, 7), 16));
	}

	private static Color hex2Argb(String colorStr) {
		return new Color(
				Integer.valueOf(colorStr.substring(3, 5), 16),
				Integer.valueOf(colorStr.substring(5, 7), 16),
				Integer.valueOf(colorStr.substring(7, 9), 16),
				Integer.valueOf(colorStr.substring(1, 3), 16));
	}

	private static Color CreateColorFromHex(String colorStr) {
		colorStr = colorStr.replaceAll(" ", "");
		if (colorStr.length() == 7)
			return hex2Rgb(colorStr);
		else if (colorStr.length() == 9)
			return hex2Argb(colorStr);
		else
			return new Color(0, 0, 0);
	}

	public static String ColorToR(String color) {
		try {
			if (color.length() == 9) {
				color = '#' + color.substring(3, color.length());
			}
			Color c = Color.decode(color);
			double red = c.getRed() / 255.0;
			return Double.toString(red);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static String ColorToG(String color) {
		try {
			if (color.length() == 9) {
				color = '#' + color.substring(3, color.length());
			}
			Color c = Color.decode(color);
			double green = c.getGreen() / 255.0;
			return Double.toString(green);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static String ColorToB(String color) {
		try {
			if (color.length() == 9) {
				color = '#' + color.substring(3, color.length());
			}
			Color c = Color.decode(color);
			double blue = c.getBlue() / 255.0;
			return Double.toString(blue);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static String ColorToAlpha(String color) {
		try {
			Color c = CreateColorFromHex(color);
			int alpha = c.getAlpha();
			float rezalt = (float) (((float) alpha) / 255.0);
			return Float.toString(rezalt);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}


	public static String GetWidthWrapContent(String content, String fontSize) {
		try {
			AffineTransform affinetransform = new AffineTransform();
			FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
			Font font = new Font("Droid", Font.PLAIN, Integer.valueOf(SizeFormatConverter(fontSize)));
			int textwidth = (int) (font.getStringBounds(content, frc).getWidth());
			//String ret = Integer.toString(textwidth + 10);
			return Integer.toString(textwidth);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static String GetHeightWrapContent(int fontSize) {
		try {
			int h = fontSize * 2;
			return Integer.toString(h);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return Integer.toString(30);
		}
	}

    public static String GetDrawableName(String str){
        if(str != null) {
            String[] str_draw = str.split("/");
            if (str_draw.length > 1) {
                return str_draw[1];
            }
        }
        return null;
    }

	private static String res_path = "";
    private static String res_project_path = "";

	public static void SetXsltResPath(String xslt_path) {
		OSXStringProcessor.res_path = xslt_path;
	}
    public static void SetProjectResPath(String project_path) {
        OSXStringProcessor.res_project_path = project_path;
    }

	public static String GetXsltResPath() {
		return OSXStringProcessor.res_path;
	}

	public static String GetStylesPath() {
		return OSXStringProcessor.res_path + "/values/styles.xml";
	}

	public static String GetDrawablePath() {
		return OSXStringProcessor.res_path + "/drawable";
	}
    public static String GetProjectDrawablePath() {
        return OSXStringProcessor.res_project_path + "/drawable";
    }
    public static String GetProjectLayoutPath() {
        return OSXStringProcessor.res_project_path + "/layout";
    }

    public static String getImage(String name){
        File inputFilePath = new File(GetProjectDrawablePath());
        if(inputFilePath.exists()) {
            File [] listImg = inputFilePath.listFiles();
			if(listImg != null) {
				for (int i = 0; i < listImg.length; i++) {
					if (listImg[i].getName().contains(name))
						return listImg[i].getName();
				}
			}
        }
        return null;
    }
}
