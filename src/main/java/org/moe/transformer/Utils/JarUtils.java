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

package org.moe.transformer.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarUtils {
    private JarUtils() {
    }

    /**
     * Returns the jar file, where resources are located
     */
    public static JarFile getJarFileByResourceName(Class<?> clazz, String resourceName) throws IOException {
        final URL jarUrl = clazz.getResource("/" + resourceName);
        final JarURLConnection connection = (JarURLConnection) jarUrl.openConnection();
        final URL url = connection.getJarFileURL();
        final File jarFile = new File(url.getFile());
        return new JarFile(jarFile);
    }

    /**
     * Copies a directory from a jar file to an external directory.
     */
    public static void copyResourcesToDirectory(JarFile fromJar, String jarDir, String destDir)
            throws IOException {

        for (Enumeration<JarEntry> entries = fromJar.entries(); entries.hasMoreElements(); ) {
            JarEntry entry = entries.nextElement();

            if (entry.getName().startsWith(jarDir) && !entry.isDirectory()) {

                File dest = new File(destDir + "/" + entry.getName());
                File parent = dest.getParentFile();
                if (parent != null) {
                    parent.mkdirs();
                }

                System.out.println("----copy-->" + dest.getAbsolutePath());

                FileOutputStream out = null;
                InputStream in = null;

                try{
                    out = new FileOutputStream(dest);
                    in = fromJar.getInputStream(entry);

                    byte[] buffer = new byte[8 * 1024];

                    int s;
                    while ((s = in.read(buffer)) > 0) {
                        out.write(buffer, 0, s);
                    }
                }
                finally {
                    if(in != null){
                        in.close();
                    }

                    if(out != null){
                        out.close();
                    }
                }
            }
        }
    }
}
