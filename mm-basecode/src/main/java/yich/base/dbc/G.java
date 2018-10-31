/*******************************************************************************
 * Copyright 2018 Joe Yichong
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

package yich.base.dbc;

/**
 * Some methods used to generate array.
 *
 * @version 1.0
 * */
public class G {

    /**
     * Generate a boolean array.
     * @param bools boolean values or expressions
     * @return a boolean array
     *
     * */
    public static boolean[] a(boolean... bools){
        return bools;
    }

    /**
     * Generate a String array.
     * @param strs String values or expressions
     * @return a String array
     *
     * */
    public static String[] a(String... strs){
        return strs;
    }
}
