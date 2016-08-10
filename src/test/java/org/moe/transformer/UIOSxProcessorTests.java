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

import org.junit.Test;
import static org.junit.Assert.*;
import org.moe.transformer.UIOSxProcessor.*;

import java.awt.*;

public class UIOSxProcessorTests {
	
	@Test
    public void RGBTransformTest() {
        assertEquals(false, false);
    }
	
	@Test
	public void ColorToRTest(){
		String res = OSXStringProcessor.ColorToR("#ff0000");
		assertEquals(res, "1.0");
	}
	
	@Test
	public void ColorToGTest(){
		String res = OSXStringProcessor.ColorToG("#00ff00");	
		assertEquals(res, "1.0");
	}
	
	@Test
	public void ColorToBTest(){
		String res = OSXStringProcessor.ColorToB("#0000ff");	
		assertEquals(res, "1.0");
	}
	
	@Test
	public void ColorToAlfaTest(){
		String res = OSXStringProcessor.ColorToAlpha("#ff000000");	
		assertEquals(res, "1.0");
	}
	
	@Test
	public void SizeFormatConverterTest(){
		String res = OSXStringProcessor.SizeFormatConverter("10dp");
		assertEquals(res, "10");
	}
}
