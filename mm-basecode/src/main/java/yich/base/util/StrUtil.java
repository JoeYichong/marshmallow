/*******************************************************************************
 * Copyright 2018 Joe Yichong (@Joe_yichong)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package yich.base.util;


import yich.base.dbc.Require;

import java.util.*;
import java.util.stream.Collectors;

public class StrUtil {
	private static final String ALPHA_NUMERIC =
			"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	public static String randomAlphaNumeric(long len) {
        Require.argument(len >= 0, len, "len >= 0");
        if (len == 0) {
            return "";
        }

		StringBuilder sb = new StringBuilder();
		Random rand = new Random();
		
		//System.out.println(ALPHA_NUMERIC.length()); // 62
		for(int i = 0; i < len; i++) {
			sb.append(ALPHA_NUMERIC.charAt(rand.nextInt(62)));
		}
		
		return sb.toString();
	}

    public static String strToJsonValue(String str) {
		return (str == null ? str : ("\"" + str + "\""));
	}

    public static String arrToJsonValue(Object[] arr) {
		if (arr == null) {
			return null;
		}
		List<String> list = Arrays.asList(arr)
				.stream()
				.map(item -> item== null ? null : "\"" + String.valueOf(item) + "\"")
				.collect(Collectors.toList());
		return "[" + String.join(", ", list) + ']';
	}

    public static String setToJsonValue(Set set) {
		return set == null ? null : arrToJsonValue(set.toArray());
	}

	public static String colToStr(Collection<String> strs) {
		StringBuilder sb = new StringBuilder();
		for (String str : strs) {
			sb.append(str);
			sb.append("\n");
		}
		return sb.toString();
	}

}
